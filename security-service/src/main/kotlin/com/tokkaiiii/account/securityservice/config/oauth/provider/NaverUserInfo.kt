package com.tokkaiiii.account.securityservice.config.oauth.provider

class NaverUserInfo(
    private val attributes: Map<String, Any> // getAttributes()
) : OAuth2UserInfo {
    override fun getProviderId(): String {
        return attributes["id"].toString()
    }

    override fun getProvider(): String {
        return "naver"
    }

    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    override fun getName(): String {
        return attributes["name"].toString()
    }
}