package com.example.demo.controller

import com.example.demo.domain.AuthInteractor
import com.example.demo.controller.model.RegistrationResponse
import com.example.demo.controller.model.UserExistsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authInteractor: AuthInteractor) {

    @PostMapping("/register")
    fun registerUser(
            @RequestParam(name = "user_name") userName: String,
            @RequestParam(name = "user_password") userPassword: String
    ): RegistrationResponse = RegistrationResponse(authInteractor.createUser(userName, userPassword))

    @GetMapping("/user_exists")
    fun isUserExist(
            @RequestParam(name = "user_name") userName: String
    ): UserExistsResponse = UserExistsResponse(authInteractor.isUserExists(userName))
}