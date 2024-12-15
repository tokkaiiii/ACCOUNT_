package com.tokkaiiii.account.securityservice.auth

import org.jooq.generated.tables.pojos.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인 진행
// 로그인 진행이 완료되면 시큐리티 session 을 만듬 -> security 가 가진 session 이 존재(Security ContextHolder 라는 key 값을 가짐)
// security 가 가진 session 에 들어갈 수 있는 타입은 정해져있음 -> Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 함
// User 객체 타입도 정해져 있음 -> UserDetails 타입 객체

// security session -> Authentication -> UserDetails


class PrincipalDetails(
   private val user: User
) : UserDetails{

    // 해당 User 의 권한을 리턴하는 메소드
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val collect = mutableListOf<GrantedAuthority>()
        collect.add(GrantedAuthority { user.role.name })
        return collect
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
    }

    // 계정 만료
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    // 비밀번호 오래됐는지
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    // 계정이 활성화 돼있는지
    override fun isAccountNonLocked(): Boolean {
        // 사이트에서 1년 동안 로그인을 안 하면 휴면 계정으로 하기로 했으면 여기서 설정
        // loginDate: LocalDateTime 같은 필드 넣어서 체크
        // user.loginDate 가져와서 현재 시간 - 로그인 시간 -> 1년 초과하면 return false
        return true
    }
}