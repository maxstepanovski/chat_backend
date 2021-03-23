package com.example.demo.domain

import com.example.demo.Role
import com.example.demo.data.AuthorityRepository
import com.example.demo.data.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.sql.Connection
import javax.sql.DataSource

class AuthInteractor constructor(
        private val dataSource: DataSource,
        private val passwordEncoder: PasswordEncoder,
        private val authorityRepository: AuthorityRepository,
        private val userRepository: UserRepository
) {

    fun createUser(userName: String, userPassword: String): Boolean {
        if (isUserNameUnique(userName).not()) {
            return false
        }
        val userError = createUserRecord(userName, userPassword)
        if (userError != null) {
            return false
        }
        val authError = createAuthorityRecord(userName)
        if (authError != null) {
            return false
        }
        return true
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

    fun isUserExists(userName: String): Boolean {
        var result = false
        var connection: Connection? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            val rs = connection.createStatement().executeQuery(
                    "select exists(select 1 from authentication.user where user_name = '$userName');"
            )
            while (rs.next()) {
                result = rs.getBoolean("exists")
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