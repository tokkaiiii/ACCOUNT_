package com.tokkaiiii.account.securityservice.dto.request

data class JoinRequest(
    val username: String,
    val password: String,
    val email: String
)
