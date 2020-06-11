package com.example.demo

import com.example.demo.data.AuthRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val repository: AuthRepository) {

    @GetMapping("/")
    fun index(): String = "Unprotected method for anyone to use"

    @GetMapping("/admin/info")
    fun adminInfo(): String = "You can see this only if you are an admin"

    @GetMapping("/user/info")
    fun userInfo(): String = "You can see this only if you are a user"

    @GetMapping("/common/info")
    fun commonInfo(): String = "You can see this if you are either an admin or a user"

    @GetMapping("/register")
    fun registerUser(
            @RequestParam(name = "user_name") userName: String,
            @RequestParam(name = "user_password") userPassword: String
    ): String {
        return repository.createUser(userName, userPassword)
    }
}