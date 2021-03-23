package com.example.demo.data

import com.example.demo.data.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AuthorityRepository: JpaRepository<AuthorityEntity, Long> {

    @Query(value = "SELECT * FROM authentication.authority WHERE user_id = ?1", nativeQuery = true)
    fun findByUserId(userId: Long): List<AuthorityEntity>
}