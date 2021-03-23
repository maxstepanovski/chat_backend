package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(name = "conversation", schema = "public")
data class ConversationEntity(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @Column(name = "conversation_name")
        var name: String
) {
    constructor() : this(0, "")
}