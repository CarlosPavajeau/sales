package com.cantte.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.JwtValidators
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Value("\${auth0.audience}")
    private val audience: String? = null

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private val issuer: String? = null

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer().jwt().decoder(jwtDecoder())
    }

    private fun jwtDecoder(): JwtDecoder {
        val withAudience = AudienceValidator(audience!!)
        val withIssuer = JwtValidators.createDefaultWithIssuer(issuer!!)
        val validator = DelegatingOAuth2TokenValidator(withAudience, withIssuer)

        val decoder = JwtDecoders.fromOidcIssuerLocation<NimbusJwtDecoder>(issuer)
        decoder.setJwtValidator(validator)

        return decoder
    }
}