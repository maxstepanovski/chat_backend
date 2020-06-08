package com.example.demo.data

import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

@Component
class AuthRepository constructor(private val dataSource: DataSource) {

    fun createUser(userName: String, userPassword: String) {
        var connection: Connection? = null
        var statement: PreparedStatement? = null
        try {
            connection = dataSource.connection
            connection.autoCommit = false
            statement = connection.prepareStatement(
                    "insert into authorization.\"user\"(user_name, user_password, enabled)" +
                            "values ('?', '?', true)"
            )
            statement.setString(1, userName)
            statement.setString(2, userPassword)
            statement.executeUpdate()
            connection.commit()
            statement.close()
        } catch (ex: Throwable) {
            ex.printStackTrace()
        } finally {
            connection?.close()
        }
    }

}