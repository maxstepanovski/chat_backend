package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
                .inMemoryAuthentication()
                .withUser(
                        User.builder()
                                .username("maksim")
                                .password(passwordEncoder().encode("123"))
                                .roles(Role.ADMIN.alias)
                                .build()
                )
                .withUser(
                        User.builder()
                                .username("alsu")
                                .password(passwordEncoder().encode("321"))
                                .roles(Role.ADMIN.alias, Role.USER.alias)
                                .build()
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
}

enum class Role(val alias: String) {
    ADMIN("admin"), USER("user")
}