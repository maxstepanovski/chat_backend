package com.example.demo.data

import com.example.demo.data.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM authentication.user WHERE user_name = ?1", nativeQuery = true)
    fun findByName(userName: String): UserEntity

    @Query(value = "SELECT EXISTS(SELECT 1 FROM authentication.user WHERE user_name = ?1)", nativeQuery = true)
    fun existsByName(userName: String): Boolean
}