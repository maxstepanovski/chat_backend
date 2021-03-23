package com.example.demo.data

import com.example.demo.data.model.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository: JpaRepository<AuthorityEntity, Long>