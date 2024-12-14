package com.tokkaiiii.account.securityservice.repository

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import org.assertj.core.api.Assertions.assertThat
import org.jooq.generated.tables.pojos.User
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
class UserRepositoryImplTest(
    @Autowired private val userRepository: UserRepository
){

    @Test
    @DisplayName("join")
    @Transactional
    fun `should save User successfully`(){

        // given
        val request = JoinRequest(
            username = "test_user",
            password = "test_password",
            email = "test_email"
        )
        val user = User()
        user.username = request.username
        user.password = request.password
        user.email = request.email

        // when
        val userId = userRepository.createUser(user)

        // then
        assertThat(userId).isNotNull()

    }

}