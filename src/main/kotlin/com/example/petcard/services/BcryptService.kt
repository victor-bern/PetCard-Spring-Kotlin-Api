package com.example.petcard.services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class BcryptService private constructor() {
    companion object {
        private val _bcrypt = BCryptPasswordEncoder()

        fun hashPassword(password: String): String {
            return _bcrypt.encode(password)
        }

        fun validatePassword(userPassword: String, requestPassword: String) =
            _bcrypt.matches(requestPassword, userPassword)
    }
}