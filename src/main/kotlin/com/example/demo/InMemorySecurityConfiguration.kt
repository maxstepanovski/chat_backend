package com.example.demo

import org.postgresql.ds.PGSimpleDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .jdbcAuthentication()
                .dataSource(createDataSource())
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "SELECT id, user_name, user_password" +
                                "FROM authority.\"user\" WHERE id = ?;"
                )
                .authoritiesByUsernameQuery(
                        "select authentication.\"user\".user_name, authentication.\"authority\".authority \n" +
                                "from authentication.\"authority\", authentication.\"user\" \n" +
                                "where authentication.\"authority\".user_id = authentication.\"user\".id and user_name = 'maksim';"
                )
    }

    override fun configure(http: HttpSecurity) {
        http
                .formLogin()
                .successForwardUrl("/")

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/*").hasRole(Role.ADMIN.alias)
                .antMatchers("/user/*").hasRole(Role.USER.alias)
                .antMatchers("/common/*").hasAnyRole(Role.ADMIN.alias, Role.USER.alias)
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun createDataSource(): DataSource {
        val dataSource = PGSimpleDataSource()
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres")
        dataSource.user = "postgres"
        dataSource.password = "123456"
        return dataSource
    }
}

enum class Role(val alias: String) {
    ADMIN("admin"), USER("user")
}