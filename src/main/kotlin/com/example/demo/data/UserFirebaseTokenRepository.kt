package com.example.demo.data

import com.example.demo.data.model.UserFirebaseTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface UserFirebaseTokenRepository: JpaRepository<UserFirebaseTokenEntity, Long> {

    fun findAllByUserId(userId: Long): List<UserFirebaseTokenEntity>

    fun findByUserId(userId: Long): UserFirebaseTokenEntity

    @Modifying
    @Query("INSERT INTO public.user_firebase_token (user_id, firebase_token) VALUES (?1, ?2) ON CONFLICT (user_id) DO UPDATE SET firebase_token = excluded.firebase_token", nativeQuery = true)
    fun insertWithReplace(userId: Long, token: String)
}