package com.example.demo.filters

import com.example.demo.AUTH_PREFIX
import com.example.demo.TOKEN_LIFESPAN_MS
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
        private val jwtAudience: String,
        private val jwtIssuer: String,
        private val jwtType: String,
        private val secretKey: SecretKey,
        authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {

    init {
        authenticationManager = authManager
        setFilterProcessesUrl("/login")
        setPostOnly(false)
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        (authResult.principal as User).apply {
            val token = Jwts.builder()
                    .signWith(secretKey, SignatureAlgorithm.HS512)
                    .setHeaderParam("type", jwtType)
                    .setIssuer(jwtIssuer)
                    .setAudience(jwtAudience)
                    .setSubject(username)
                    .setExpiration(Date(System.currentTimeMillis() + TOKEN_LIFESPAN_MS))
                    .compact()
            response.addHeader(HttpHeaders.AUTHORIZATION, "$AUTH_PREFIX$token")
        }
    }
}