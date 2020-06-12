package com.example.demo.data

import com.example.demo.Role
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

class AuthRepository constructor(
        private val dataSource: DataSource,
        private val passwordEncoder: PasswordEncoder
) {

    fun createUser(userName: String, userPassword: String): String {
        if (isUserNameUnique(userName).not()) {
            return "user with name $userName is already registered!"
        }
        val userError = createUserRecord(userName, userPassword)
        if (userError != null) {
            return userError
        }
        val authError = createAuthorityRecord(userName)
        if (authError != null) {
            return authError
        }
        return "successful registration!"
    }

    fun readUserAuthorities(userName: String): MutableList<String> {
        val result = mutableListOf<String>()
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val rs = connection.createStatement().executeQuery(
                    "select authority from authentication.authority where user_id = (select id from authentication.user where user_name = '$userName')"
            )
            while (rs.next()) {
                rs.getString("authority").let { result.add(it) }
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        } finally {
            connection?.close()
            return result
        }
    }

    private fun createUserRecord(userName: String, userPassword: String): String? {
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val createUserStatement = connection.prepareStatement(
                    "insert into authentication.user (user_name, user_password, enabled) values (?, ?, true)"
            )
            createUserStatement.setString(1, userName)
            createUserStatement.setString(2, passwordEncoder.encode(userPassword))
            createUserStatement.executeUpdate()
            connection.commit()
            createUserStatement.close()
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return "registration error!\n\n" + ex.stackTrace
        } finally {
            connection?.close()
        }
        return null
    }

    private fun createAuthorityRecord(userName: String): String? {
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val createAuthoritySt = connection.prepareStatement(
                    "insert into authentication.authority (user_id, authority) values ((select id from authentication.user where user_name = ?), ?)"
            )
            createAuthoritySt.setString(1, userName)
            createAuthoritySt.setString(2, Role.USER.alias)
            createAuthoritySt.executeUpdate()
            connection.commit()
            createAuthoritySt.close()
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return "registration error!\n\n" + ex.stackTrace
        } finally {
            connection?.close()
        }
        return null
    }

    private fun isUserNameUnique(userName: String): Boolean {
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val rs = connection.createStatement().executeQuery("select * from authentication.user where user_name = '$userName'")
            if (rs.next()) {
                return false
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return false
        } finally {
            connection?.close()
        }
        return true
    }
}