package com.example.demo

import com.example.demo.data.AuthRepository
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
    fun authRepository(dataSource: DataSource, passwordEncoder: PasswordEncoder) = AuthRepository(dataSource, passwordEncoder)

    @Bean
    fun jwtSecretKey(): SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)
}