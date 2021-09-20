package com.example.demo

import com.example.demo.controller.DialogSocketController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfiguration : WebSocketConfigurer {

    @Autowired
    lateinit var dialogSocketController: DialogSocketController

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(dialogSocketController, "/conversation_socket")
    }
}