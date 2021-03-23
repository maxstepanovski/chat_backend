package com.example.demo

import com.example.demo.data.*
import com.example.demo.domain.AuthInteractor
import com.example.demo.domain.MainInteractor
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey
import javax.sql.DataSource

@Configuration
class BeanConfiguration {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun dataSource(): DataSource {
        val dataSource = PGSimpleDataSource()
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres")
        dataSource.user = "postgres"
        dataSource.password = "123456"
        return dataSource
    }

    @Bean
    fun authRepository(
            passwordEncoder: PasswordEncoder,
            authorityRepository: AuthorityRepository,
            userRepository: UserRepository
    ) = AuthInteractor(passwordEncoder, authorityRepository, userRepository)

    @Bean
    fun mainRepository(
            conversationRepository: ConversationRepository,
            conversationMessageRepository: ConversationMessageRepository,
            messageRepository: MessageRepository,
            userRepository: UserRepository,
            userConversationRepository: UserConversationRepository
    ) = MainInteractor(
            conversationRepository,
            userConversationRepository,
            messageRepository,
            conversationMessageRepository,
            userRepository
    )

    @Bean
    fun jwtSecretKey(): SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)
}