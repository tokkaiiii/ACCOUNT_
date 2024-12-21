package com.tokkaiiii.account.securityservice.repository

import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.generated.tables.User_.USER
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
            USER.EMAIL,
            USER.PROVIDER,
            USER.PROVIDER_ID
        ).values(
            user.username,
            user.password,
            user.email,
            user.provider,
            user.providerId
        ).returningResult(USER.ID)
            .fetchOneInto(Long::class.java)
    }


    override fun findByUsername(username: String): User? {
        return dslContext.selectFrom(USER)
            .where(USER.USERNAME.eq(username))
            .fetchOneInto(User::class.java)
    }
}