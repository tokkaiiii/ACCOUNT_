package com.tokkaiiii.account.securityservice.controller

import com.tokkaiiii.account.securityservice.auth.PrincipalDetails
import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import com.tokkaiiii.account.securityservice.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
    private val userService: UserService
) {

    @ResponseBody
    @GetMapping("/test/login")
    fun loginTest(
        authentication: Authentication,
        @AuthenticationPrincipal userDetails: PrincipalDetails,
    ): String {
        println("/test/login===================")
        val principalDetails = authentication.principal as PrincipalDetails
        println("authentication: ${principalDetails.getUser()}")
        println("============================")
        println("userDetails: ${userDetails.getUser()}")
        return "세션 정보 확인하기"
    }

    @ResponseBody
    @GetMapping("/test/oauth/login")
    fun loginOAuthTest(
        authentication: Authentication,
        @AuthenticationPrincipal oauth: OAuth2User,
    ): String {
        println("/test/login===================")
        val oAuth2User = authentication.principal as OAuth2User
        println("authentication: ${oAuth2User.attributes}")
        println("================")
        println("oauth2User: ${oauth.attributes}")
        return "OAuth 세션 정보 확인하기"
    }


    @GetMapping
    fun index() = "index"

    @ResponseBody
    @GetMapping("/user")
    fun user(@AuthenticationPrincipal principalDetails: PrincipalDetails) {
        println("userDetails: ${principalDetails.getUser()}")
    }

    @ResponseBody
    @GetMapping("/admin")
    fun admin() = "admin"

    @ResponseBody
    @GetMapping("/manager")
    fun manager() = "manager"

    @GetMapping("/loginForm")
    fun loginForm() = "loginForm"

    @GetMapping("/joinForm")
    fun joinForm() = "joinForm"

    @PostMapping("/join")
    fun join(@ModelAttribute request: JoinRequest): String{
        userService.creatUser(request)
        return "redirect:/loginForm"
    }

}