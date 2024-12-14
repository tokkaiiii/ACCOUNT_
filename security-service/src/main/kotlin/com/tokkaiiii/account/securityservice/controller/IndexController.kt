package com.tokkaiiii.account.securityservice.controller

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import com.tokkaiiii.account.securityservice.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
    private val userService: UserService
) {

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