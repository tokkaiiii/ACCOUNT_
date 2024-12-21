package com.tokkaiiii.account.securityservice.config.oauth.provider

interface OAuth2UserInfo {
    fun getProviderId(): String
    fun getProvider(): String
    fun getEmail(): String
    fun getName(): String
}