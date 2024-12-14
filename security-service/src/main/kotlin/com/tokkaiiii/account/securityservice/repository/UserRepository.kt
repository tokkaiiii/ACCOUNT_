package com.tokkaiiii.account.securityservice.repository

import org.jooq.generated.tables.pojos.User


interface UserRepository {

    fun createUser(user: User): Long?

}