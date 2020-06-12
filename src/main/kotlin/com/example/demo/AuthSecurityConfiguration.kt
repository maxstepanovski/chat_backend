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
class AuthSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource())
                .passwordEncoder(passwordEncoder())
                .rolePrefix("ROLE_")
                .usersByUsernameQuery(
                        "SELECT user_name, user_password, enabled " +
                                "FROM authentication.user WHERE user_name = ?"
                )
                .authoritiesByUsernameQuery(
                        "select authentication.user.user_name, authentication.authority.authority " +
                                "from authentication.authority, authentication.user " +
                                "where authentication.authority.user_id = authentication.user.id and user_name = ?"
                )
    }

    override fun configure(http: HttpSecurity) {
        http
                .formLogin().defaultSuccessUrl("/common/info", true)
                .and().rememberMe()
                .and().logout().logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/${Role.ADMIN.alias}/*").hasRole(Role.ADMIN.alias)
                .antMatchers("/${Role.USER.alias}/*").hasRole(Role.USER.alias)
                .antMatchers("/common/*").hasAnyRole(Role.ADMIN.alias, Role.USER.alias)
    }

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
}

enum class Role(val alias: String) {
    ADMIN("admin"), USER("user")
}