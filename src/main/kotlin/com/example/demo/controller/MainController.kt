package com.example.demo.controller

import com.example.demo.data.model.IndexResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/index")
    fun index(): IndexResponse = IndexResponse(
            "Server is working",
            "Use different endpoints to access data"
    )

    @GetMapping("/admin/info")
    fun adminInfo(): String = "You can see this only if you are an admin"

    @GetMapping("/user/info")
    fun userInfo(): String = "You can see this only if you are a user"

    @GetMapping("/common/info")
    fun commonInfo(): String = "You can see this if you are either an admin or a user"
}