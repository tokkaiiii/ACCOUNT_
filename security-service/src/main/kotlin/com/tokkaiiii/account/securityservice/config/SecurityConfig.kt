package com.tokkaiiii.account.securityservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.http.SessionCreationPolicy.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터 -> 기본 스프링 필터체인에 등록
class SecurityConfig {

    @Bean
    fun passwordEncoder()= BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/user/**").authenticated()  // /admin/** 경로에 대해서 ADMIN 역할만 접근 가능
                    .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")  // /user/** 경로에 대해 USER 또는 ADMIN 역할만 접근 가능
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()  // 나머지 요청은 허용
            }
            .formLogin {
                it.loginPage("/loginForm")
                it.loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행
                    .defaultSuccessUrl("/") // 로그인 성공하면 / 메인으로 보내기
            }
            .build()
    }
}
