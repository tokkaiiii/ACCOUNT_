package com.tokkaiiii.account.securityservice.repository

import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.generated.tables.User_
import org.jooq.generated.tables.User_.*
import org.jooq.generated.tables.daos.UserDao
import org.jooq.generated.tables.pojos.User
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val dslContext: DSLContext,
    configuration: Configuration
) : UserRepository {

    private val dao: UserDao = UserDao(configuration)

    override fun createUser(user: User): Long? {
        return dslContext.insertInto(
            USER,
            USER.USERNAME,
            USER.PASSWORD,
            USER.EMAIL
        ).values(
            user.username,
            user.password,
            user.email
        ).returningResult(USER.ID)
            .fetchOneInto(Long::class.java)
    }
}