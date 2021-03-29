package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "public", name = "user_firebase_token")
data class UserFirebaseTokenEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "user_id")
        var userId: Long,

        @Column(name = "firebase_token")
        var firebaseToken: String
) {
    constructor() : this(0, 0, "")
}
