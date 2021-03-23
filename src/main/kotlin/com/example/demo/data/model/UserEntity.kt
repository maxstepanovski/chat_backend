package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "authentication", name = "user")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "user_password")
        var userPassword: String,

        @Column(name = "user_name")
        var userName: String,

        @Column(name = "enabled")
        var isEnabled: Boolean
) {
    constructor() : this(0, "", "", true)
}
