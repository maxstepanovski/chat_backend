package com.example.demo

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)

        auth
                .inMemoryAuthentication()
                .withUser(
                        User.builder()
                                .username("maksim")
                                .password("12345")
                                .roles(Role.ADMIN.alias)
                                .build()
                )
                .withUser(
                        User.builder()
                                .username("alsu")
                                .password("54321")
                                .roles(Role.ADMIN.alias, Role.USER.alias)
                                .build()
                )
                .passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        super.configure(http)

        http
                .formLogin()

        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole(Role.ADMIN.alias)
                .antMatchers("/user/*").hasRole(Role.USER.alias)
                .antMatchers("/").permitAll()
    }
}

enum class Role(val alias: String) {
    ADMIN("admin"), USER("user")
}