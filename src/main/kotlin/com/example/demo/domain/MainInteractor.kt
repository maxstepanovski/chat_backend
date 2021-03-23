package com.example.demo.domain

import com.example.demo.data.ConversationRepository
import com.example.demo.data.UserRepository
import com.example.demo.data.model.Conversation
import java.sql.Connection
import javax.sql.DataSource

class MainInteractor(
        private val dataSource: DataSource,
        private val conversationRepository: ConversationRepository,
        private val userRepository: UserRepository
) {

    fun getUsers() = userRepository.findAll().toString()

    fun getConversations() = conversationRepository.findAll().toString()

    fun getUserConversations(username: String): List<Conversation> {
        val result = mutableListOf<Conversation>()
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val conversationIds = connection.createStatement().executeQuery(
                    "SELECT (conversation_id) FROM public.user_conversation WHERE user_id = " +
                            "(SELECT (id) FROM authentication.user WHERE user_name = '$username')"
            )
            while (conversationIds.next()) {
                val conversationId = conversationIds.getLong(CONVERSATION_ID)
                val conversation = connection.createStatement().executeQuery(
                        "SELECT * FROM public.conversation WHERE id = $conversationId"
                )
                while (conversation.next()) {
                    result.add(
                            Conversation(
                                    conversation.getLong(ID),
                                    conversation.getString(CONVERSATION_NAME)
                            )
                    )
                }
            }
            connection.commit()
        } catch (ex: Throwable) {
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }

    fun createNewMessage(principalName: String, userName: String, message: String, conversationTitle: String?): Boolean {
        var connection: Connection? = null
        return try {
            connection = dataSource.connection
            connection.autoCommit = false

            with(connection) {
                val conversationIdCursor = createStatement().executeQuery(
                        "INSERT INTO public.conversation (conversation_name) VALUES ('${conversationTitle.orEmpty()}') RETURNING id;"
                )
                val userIdCursor = createStatement().executeQuery(
                        "SELECT (id) FROM authentication.user WHERE user_name = '$userName';"
                )
                val principalIdCursor = createStatement().executeQuery(
                        "SELECT (id) FROM authentication.user WHERE user_name = '$principalName';"
                )
                conversationIdCursor.next()
                userIdCursor.next()
                principalIdCursor.next()

                val conversationId = conversationIdCursor.getLong(ID)
                val userId = userIdCursor.getLong(ID)
                val principalId = principalIdCursor.getLong(ID)
                val timeMillis = System.currentTimeMillis()
                commit()

                createStatement().executeQuery(
                        "INSERT INTO public.user_conversation (user_id, conversation_id) VALUES ($principalId, $conversationId);"
                )
                createStatement().executeQuery(
                        "INSERT INTO public.user_conversation (user_id, conversation_id) VALUES ($userId, $conversationId);"
                )
                val messageIdCursor = createStatement().executeQuery(
                        "INSERT INTO public.message (message_text, message_time, user_id) VALUES ('$message', $timeMillis, $principalId) RETURNING id;"
                )
                messageIdCursor.next()
                val messageId = messageIdCursor.getLong(ID)
                commit()

                createStatement().executeQuery(
                        "INSERT INTO public.conversation_message (conversation_id, message_id) VALUES ($conversationId, $messageId);"
                )
                commit()
            }
            true
        } catch (ex: Throwable) {
            ex.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }

    companion object {
        private const val ID = "id"
        private const val CONVERSATION_ID = "conversation_id"
        private const val CONVERSATION_NAME = "conversation_name"
    }
}