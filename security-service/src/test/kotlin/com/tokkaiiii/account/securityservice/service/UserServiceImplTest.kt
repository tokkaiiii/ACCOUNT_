package com.tokkaiiii.account.securityservice.service

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class UserServiceImplTest(
    @Autowired private val userService: UserService
){

    @Test
    @Transactional
    @DisplayName("join")
    fun `should create User successfully`(){
        // given
        val request = JoinRequest(
            username = "test_user",
            password = "test_password",
            email = "test_email"
        )
        // when
        val userId = userService.creatUser(request)

        // then
        assertThat(userId).isNotNull()
    }

}
