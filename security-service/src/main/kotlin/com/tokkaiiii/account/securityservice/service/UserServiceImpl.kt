package com.tokkaiiii.account.securityservice.service

import com.tokkaiiii.account.securityservice.dto.request.JoinRequest
import com.tokkaiiii.account.securityservice.repository.UserRepository
import org.jooq.generated.tables.pojos.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional
    override fun creatUser(request: JoinRequest): Long? {
        val user = User()
        user.username = request.username
        user.password = passwordEncoder.encode(request.password)
        user.email = request.email
        return userRepository.createUser(user)
    }
}