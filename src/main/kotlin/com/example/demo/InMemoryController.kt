package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InMemoryController {

    @GetMapping("/")
    fun index(): String = "Unprotected method for anyone to use"

    @GetMapping("/admin/info")
    fun adminInfo(): String = "You can see this only if you are an admin"

    @GetMapping("/user/info")
    fun userInfo(): String = "You can see this only if you are a user"

    @GetMapping("/common/info")
    fun commonInfo(): String = "You can see this if you are either an admin or a user"
}