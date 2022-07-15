package com.example.petcard.controllers

import com.example.petcard.controllers.dto.LoginRequestDto
import com.example.petcard.repositories.UserRepository
import com.example.petcard.services.BcryptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @PostMapping(
        headers = ["Accept=*/*", "Content-Type=application/json"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun auth(@RequestBody params: LoginRequestDto): Any {
        try {
            val user = userRepository.findByEmail(params.email) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf(Pair("error", "email or passsword incorrects")))

            if (BcryptService.validatePassword(user.password, params.password)) {
                return ResponseEntity.status(HttpStatus.OK).body(user)
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("")
        } catch (e: Exception) {
            return ResponseEntity.ok(e.message)
        }


    }


}