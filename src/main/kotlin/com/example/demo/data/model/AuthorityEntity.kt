package com.example.demo.data.model

import javax.persistence.*

@Entity
@Table(schema = "public", name = "authority")
data class AuthorityEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long,

        @Column(name = "user_id")
        var userId: Long,

        @Column(name = "authority")
        var authority: String
) {
    constructor() : this(0, 0, "")
}