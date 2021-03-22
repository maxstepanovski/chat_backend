package com.example.demo.data

import com.example.demo.data.model.Conversation
import java.sql.Connection
import javax.sql.DataSource

class MainRepository(
        private val dataSource: DataSource
) {

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
        } catch (ex: Throwable) {
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }

    companion object {
        private const val ID = "id"
        private const val CONVERSATION_ID = "conversation_id"
        private const val CONVERSATION_NAME = "conversation_name"
    }
}