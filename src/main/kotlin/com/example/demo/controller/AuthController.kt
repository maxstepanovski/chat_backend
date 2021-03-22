package com.example.demo.controller

import com.example.demo.data.AuthRepository
import com.example.demo.data.model.RegistrationResponse
import com.example.demo.data.model.UserExistsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val repository: AuthRepository) {

    @PostMapping("/register")
    fun registerUser(
            @RequestParam(name = "user_name") userName: String,
            @RequestParam(name = "user_password") userPassword: String
    ): RegistrationResponse = RegistrationResponse(repository.createUser(userName, userPassword))

    @GetMapping("/user_exists")
    fun isUserExist(
            @RequestParam(name = "user_name") userName: String
    ): UserExistsResponse = UserExistsResponse(repository.isUserExists(userName))
}