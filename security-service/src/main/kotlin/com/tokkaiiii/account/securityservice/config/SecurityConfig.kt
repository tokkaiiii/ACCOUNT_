package com.tokkaiiii.account.securityservice.config

import com.tokkaiiii.account.securityservice.config.oauth.PrincipalOauth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터 -> 기본 스프링 필터체인에 등록
class SecurityConfig(
    private val principalOauth2UserService: PrincipalOauth2UserService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(STATELESS) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/user/**")
                    .authenticated()  // /admin/** 경로에 대해서 ADMIN 역할만 접근 가능
                    .requestMatchers("/manager/**")
                    .hasAnyRole("ADMIN", "MANAGER")  // /user/** 경로에 대해 USER 또는 ADMIN 역할만 접근 가능
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()  // 나머지 요청은 허용
            }
            .formLogin { formLoginConfigurer ->
                formLoginConfigurer.loginPage("/loginForm")
                formLoginConfigurer.loginProcessingUrl("/login") // login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행
                formLoginConfigurer.defaultSuccessUrl("/") // 로그인 성공하면 / 메인으로 보내기
            }
            .oauth2Login { formLoginConfigurer ->
                formLoginConfigurer.loginPage("/loginForm") // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드X, (엑세스토큰 + 사용자프로필정보 O)
                formLoginConfigurer.userInfoEndpoint{ it.userService(principalOauth2UserService) }                                                                          // 1. 코드받기(인증),
                                                                                            // 2. 엑세스토큰(권한) ,
                                                                                            // 3. 사용자 프로필 정보를 가져와서
                                                                                            // 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
            }                                                                               // 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집주소), 백화점몰 -> (vip 등급, 일반등급)
            .build()
    }
}
