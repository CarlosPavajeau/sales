package com.cantte.security

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult
import org.springframework.security.oauth2.jwt.Jwt

class AudienceValidator(private val audience: String) : OAuth2TokenValidator<Jwt> {

    override fun validate(token: Jwt): OAuth2TokenValidatorResult {
        val audiences = token.audience
        if (audiences.contains(audience)) {
            return OAuth2TokenValidatorResult.success()
        }

        val error = OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN)
        return OAuth2TokenValidatorResult.failure(error)
    }
}