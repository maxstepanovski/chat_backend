package com.example.demo.domain

import com.example.demo.Role
import com.example.demo.data.AuthorityRepository
import com.example.demo.data.UserRepository
import com.example.demo.data.model.AuthorityEntity
import com.example.demo.data.model.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

class AuthInteractor constructor(
        private val passwordEncoder: PasswordEncoder,
        private val authorityRepository: AuthorityRepository,
        private val userRepository: UserRepository
) {

    fun createUser(userName: String, userPassword: String): Boolean {
        try {
            if (isUserExists(userName)) {
                return false
            }
            createUserRecord(userName, userPassword)
            return true
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return false
        }
    }

    fun readUserAuthorities(userName: String): MutableList<String> {
        val result = mutableListOf<String>()
        val user = userRepository.findByUserName(userName)
        val authorities = authorityRepository.findByUserId(user.id)
        authorities.forEach {
            result.add(it.authority)
        }
        return result
    }

    fun isUserExists(userName: String): Boolean {
        return userRepository.existsByUserName(userName)
    }

    private fun createUserRecord(userName: String, userPassword: String) {
        val newUser = userRepository.save(UserEntity(0, passwordEncoder.encode(userPassword), userName, true))
        authorityRepository.save(AuthorityEntity(0, newUser.id, Role.USER.alias))
    }
}