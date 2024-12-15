package com.tokkaiiii.account.securityservice.service

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import org.jooq.generated.tables.pojos.User

interface UserService {

    fun creatUser(joinRequest: JoinRequest): Long?

    fun findByUsername(username: String): User?
}