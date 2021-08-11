package com.example.demo

import com.example.demo.data.*
import com.example.demo.domain.AuthInteractor
import com.example.demo.domain.FcmInteractor
import com.example.demo.domain.MainInteractor
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.crypto.SecretKey
import javax.sql.DataSource

@Configuration
class BeanConfiguration {

    @Autowired
    lateinit var env: Environment

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun dataSource(): DataSource = PGSimpleDataSource().apply {
        setUrl(env.getProperty("spring.datasource.url"))
        user = env.getProperty("spring.datasource.username")
        password = env.getProperty("spring.datasource.password")
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
    fun fcmInteractor(): FcmInteractor = FcmInteractor()
}