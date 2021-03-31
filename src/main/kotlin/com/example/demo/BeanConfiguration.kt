package com.example.demo

import com.example.demo.data.*
import com.example.demo.domain.AuthInteractor
import com.example.demo.domain.FcmInteractor
import com.example.demo.domain.MainInteractor
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey
import javax.sql.DataSource

@Configuration
class BeanConfiguration {

    @Value("fcm.file.path")
    private lateinit var fcmFilePath: String

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
            fcmInteractor: FcmInteractor,
            conversationRepository: ConversationRepository,
            conversationMessageRepository: ConversationMessageRepository,
            messageRepository: MessageRepository,
            userRepository: UserRepository,
            userConversationRepository: UserConversationRepository,
            userFirebaseTokenRepository: UserFirebaseTokenRepository
    ) = MainInteractor(
            fcmInteractor,
            conversationRepository,
            userConversationRepository,
            messageRepository,
            conversationMessageRepository,
            userRepository,
            userFirebaseTokenRepository
    )

    @Bean
    fun jwtSecretKey(): SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    @Bean
    fun fcmInteractor(): FcmInteractor = FcmInteractor("src/main/resources/fcm.json")
}