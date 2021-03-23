package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "public", name = "user_conversation")
data class UserConversationEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "user_id")
        var userId: Long,

        @Column(name = "conversation_id")
        var conversationId: Long
) {
    constructor() : this(0, 0, 0)
}
