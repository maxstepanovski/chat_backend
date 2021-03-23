package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "public", name = "conversation_message")
data class ConversationMessageEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "conversation_id")
        var conversationId: Long,

        @Column(name = "message_id")
        var messageId: Long
) {
    constructor() : this(0, 0, 0)
}
