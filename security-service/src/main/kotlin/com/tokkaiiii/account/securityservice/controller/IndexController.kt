package com.tokkaiiii.account.securityservice.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController {

    @GetMapping
    fun index() = "index"

    @ResponseBody
    @GetMapping("/user")
    fun user() = "user"

    @ResponseBody
    @GetMapping("/admin")
    fun admin() = "admin"

    @ResponseBody
    @GetMapping("/manager")
    fun manager() = "manager"

    @GetMapping("/login")
    fun login() = "loginForm"

    @ResponseBody
    @GetMapping("/join")
    fun join() = "join"

    @ResponseBody
    @GetMapping("/joinProc")
    fun joinProc() = "회원가입 완료됨"

}