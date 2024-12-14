package com.tokkaiiii.account.securityservice.service

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest

interface UserService {

    fun creatUser(joinRequest: JoinRequest): Long?

}