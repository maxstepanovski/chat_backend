package com.example.demo.filters

import com.example.demo.data.AuthRepository
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthorizationFilter(
        private val repository: AuthRepository,
        private val secretKey: SecretKey,
        authManager: AuthenticationManager
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val userNameAuthToken = parseToken(request)
        if (userNameAuthToken == null) {
            SecurityContextHolder.clearContext()
        } else {
            SecurityContextHolder.getContext().authentication = userNameAuthToken
        }
        chain.doFilter(request, response)
    }

    private fun parseToken(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        if (authHeader.startsWith(AUTH_PREFIX).not()) {
            return null
        }
        val token = authHeader.replace(AUTH_PREFIX, "")
        try {
            val claimsJws = Jwts
                    .parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)

            val userName = claimsJws.body.subject
            if (userName.isNullOrEmpty()) {
                return null
            }

            val authorities = repository.readUserAuthorities(userName).map { SimpleGrantedAuthority(it) }

            return UsernamePasswordAuthenticationToken(userName, null, authorities)
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
        return null
    }
}

private const val AUTH_PREFIX = "Bearer "