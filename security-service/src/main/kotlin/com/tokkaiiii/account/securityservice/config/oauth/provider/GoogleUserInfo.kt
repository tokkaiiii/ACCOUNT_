package com.tokkaiiii.account.securityservice.config.oauth.provider

class GoogleUserInfo(
    private val attributes: Map<String, Any> // getAttributes()
) : OAuth2UserInfo {
    override fun getProviderId(): String {
        return attributes["sub"].toString()
    }

    override fun getProvider(): String {
        return "google"
    }

    override fun getEmail(): String {
        return attributes["email"].toString()
    }

    override fun getName(): String {
        return attributes["name"].toString()
    }
}