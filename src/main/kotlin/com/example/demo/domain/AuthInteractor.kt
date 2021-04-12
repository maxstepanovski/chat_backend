package com.example.demo.domain

import com.example.demo.Role
import com.example.demo.data.AuthorityRepository
import com.example.demo.data.UserRepository
import com.example.demo.data.model.AuthorityEntity
import com.example.demo.data.model.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional

open class AuthInteractor constructor(
        private val passwordEncoder: PasswordEncoder,
        private val authorityRepository: AuthorityRepository,
        private val userRepository: UserRepository
) {

    @Transactional
    open fun createUser(userName: String, userPassword: String): Boolean {
        try {
            if (userRepository.existsByUserName(userName)) {
                return false
            }
            createUserRecord(userName, userPassword)
            return true
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return false
        }
    }

    @Transactional
    open fun readUserAuthorities(userName: String): MutableList<String> {
        val result = mutableListOf<String>()
        val user = userRepository.findByUserName(userName)
        val authorities = authorityRepository.findByUserId(user.id)
        authorities.forEach {
            result.add(it.authority)
        }
        return result
    }

    private fun createUserRecord(userName: String, userPassword: String) {
        val newUser = userRepository.save(UserEntity(0, passwordEncoder.encode(userPassword), userName, true))
        authorityRepository.save(AuthorityEntity(0, newUser.id, Role.USER.alias))
    }
}