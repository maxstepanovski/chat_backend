package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "public", name = "message")
data class MessageEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "message_text")
        var text: String,

        @Column(name = "message_time")
        var time: Long,

        @Column(name = "user_id")
        var userId: Long
) {
    constructor() : this(0, "", 0, 0)
}
