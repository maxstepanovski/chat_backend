package com.example.demo.controller

import com.example.demo.controller.converter.SocketMessageConverter
import com.example.demo.controller.model.InfoSocketResponse
import com.example.demo.controller.model.NewMessageSocketRequest
import com.example.demo.domain.MainInteractor
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class DialogSocketController(
    private val mainInteractor: MainInteractor,
    private val converter: SocketMessageConverter
) : TextWebSocketHandler() {

    private val userSessions = ConcurrentHashMap<String, WebSocketSession>()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        super.handleTextMessage(session, message)
        val request = converter.convert(message.payload)
        val principal = session.principal ?: throw RuntimeException("пользователь не авторизован")
        if (request is NewMessageSocketRequest) {
            val response = mainInteractor.createMessage(
                principal.name,
                request.message,
                request.conversationId
            )
            mainInteractor.getConversationUsers(request.conversationId)
                .map {
                    if (userSessions.containsKey(it.userName)) {
                        val responseMessage = converter.convert(principal.name, it.userName == principal.name, response)
                        userSessions[it.userName]?.sendMessage(responseMessage)
                    } else {
                        mainInteractor.sendNotification(principal.name, request.message, request.conversationId, it.id)
                    }
                }
        } else {
            throw IllegalArgumentException("неверный формат данных")
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        val principal = session.principal
        if (principal == null) {
            session.close()
            throw RuntimeException("пользователь не авторизован")
        } else {
            userSessions[principal.name] = session
            session.sendMessage(converter.convert(InfoSocketResponse("соединение установлено")))
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        val principal = session.principal
        if (principal != null) {
            userSessions.remove(principal.name)
        }
    }
}