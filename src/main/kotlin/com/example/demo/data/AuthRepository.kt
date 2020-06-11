package com.example.demo.data

import com.example.demo.Role
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

@Component
class AuthRepository constructor(
        private val dataSource: DataSource,
        private val passwordEncoder: PasswordEncoder
) {

    fun createUser(userName: String, userPassword: String): String {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            statement = connection.prepareStatement(
                    "insert into authentication.user (user_name, user_password, enabled) values (?, ?, true)"
            )
            statement.setString(1, userName)
            statement.setString(2, passwordEncoder.encode(userPassword))
            statement.executeUpdate()
            connection.commit()
            statement.close()

            statement = connection.prepareStatement(
                    "insert into authentication.authority (user_id, authority) values ((select id from authentication.user where user_name = ?), ?)"
            )
            statement.setString(1, userName)
            statement.setString(2, Role.USER.alias)
            statement.executeUpdate()
            connection.commit()
            statement.close()

        } catch (ex: Throwable) {
            ex.printStackTrace()
            return "registration error!\n\n" + ex.stackTrace
        } finally {
            connection?.close()
        }
        return "successful registration!"
    }

}