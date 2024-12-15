package com.tokkaiiii.account.securityservice.auth

import com.tokkaiiii.account.securityservice.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
// security 설정에서 loginProcessUrl("/login")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 loC 되어 있는 loadUserByUsername 함수가 실행
@Service
class PrincipalDetailsService(
    private val userService: UserService
) : UserDetailsService {
    // 로그인의 input 타입의 name 을 username 으로 해야 맞아서 들어온다
    // 변경하려면 security config 에서 loginPage 메소드 사용한 쪽에 usernameParameter("email") 이렇게 지정
    // return 하면 security session -> Authentication(UserDetails) 이렇게 들어감
    // 그리고 session(Authentication) 이렇게 들어가게 됨 / 순차적으로
    // 결과적으로 로그인 완료
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByUsername(username)?: throw UsernameNotFoundException("User $username not found")
        return PrincipalDetails(user)
    }

}