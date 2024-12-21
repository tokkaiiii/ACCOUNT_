package com.tokkaiiii.account.securityservice.config.oauth

import com.tokkaiiii.account.securityservice.auth.PrincipalDetails
import com.tokkaiiii.account.securityservice.config.oauth.provider.GoogleUserInfo
import com.tokkaiiii.account.securityservice.config.oauth.provider.OAuth2UserInfo
import com.tokkaiiii.account.securityservice.repository.UserRepository
import org.jooq.generated.enums.UserRole
import org.jooq.generated.enums.UserRole.*
import org.jooq.generated.tables.pojos.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class PrincipalOauth2UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        println("userRequest=$userRequest")
        println("clientRegistration: ${userRequest?.clientRegistration}") // registrationId 로 어떤 OAuth 로 로그인 했는지 확인가능
        println("accessToken: ${userRequest?.accessToken?.tokenValue}")
        val oAuth2User = super.loadUser(userRequest)
        // 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code 를 리턴 ( OAuth-Client 라이브러리 ) -> AccessToken 요청
        // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아준다.
        println("attributes: ${oAuth2User.attributes}")
        var oAuth2UserInfo: OAuth2UserInfo? = null
        if (userRequest?.clientRegistration?.registrationId == "google"){
            println("구글 로그인 요청")
            oAuth2UserInfo = GoogleUserInfo(oAuth2User.attributes)
        }else{
            println("우리는 구글만 지원합니다.")
        }

        val provider = oAuth2UserInfo?.getProvider()
        val providerId = oAuth2UserInfo?.getProviderId()
        val username = provider + "_" + providerId
        val password = passwordEncoder.encode("겟인데어")
        val email = oAuth2UserInfo?.getEmail()
        val role = ROLE_USER
        var user = userRepository.findByUsername(username)
        if (user == null) {
            user = User()
            user.username = username
            user.password = password
            user.email = email
            user.role = role
            user.provider = provider
            user.providerId = providerId
            userRepository.createUser(user)
        }
        // 회원가입 강제 진행
        return PrincipalDetails(user, oAuth2User.attributes)
    }
}