package com.tokkaiiii.account.securityservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncodeConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

}