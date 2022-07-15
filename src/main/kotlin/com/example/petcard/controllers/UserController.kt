package com.example.petcard.controllers

import com.example.petcard.controllers.dto.ChangePasswordRequestDto
import com.example.petcard.controllers.dto.SignUpResponseDto
import com.example.petcard.model.User
import com.example.petcard.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class UserController {
    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/users")
    fun getAll(): Any = ResponseEntity.ok(userRepository.findAll())

    @GetMapping("/users/{id}/pets")
    fun getAllUserPets(@PathVariable("id") id: Long): Any {
        val user = userRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                Pair("error", "user not found")
            )
        )
        return ResponseEntity.ok(user.pets)
    }

    @PostMapping("/users")
    fun addUser(@RequestBody user: User): ResponseEntity<Any> {
        val userCreated = userRepository.save(user)
        return ResponseEntity.ok(SignUpResponseDto(userCreated.id, userCreated.email, userCreated.pets))
    }

    @PutMapping("/users/{id}")
    fun updateUser(
        @RequestBody user: User,
        @PathVariable(value = "id") id: Long
    ): Any {
        val userInDb =
            userRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf(Pair("error", "user not found")))
        userInDb.email = user.email
        userInDb.name = user.name
        userRepository.save(userInDb)
        return ResponseEntity.ok(userInDb)
    }

    @DeleteMapping("/users/{id}", headers = ["Accept=*/*", "Content-Type=application/json"])
    fun delete(@PathVariable(value = "id") id: Long): Any {
        val userInDb = userRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "user not found")))

        userRepository.delete(userInDb)
        return ResponseEntity.noContent()
    }

    @PutMapping("/users/{id}/change_password", headers = ["Accept=*/*", "Content-Type=application/json"])
    fun changePassword(
        @PathVariable(value = "id") id: Long,
        @RequestBody requestPassDto: ChangePasswordRequestDto
    ): Any {
        try {
            val userInDb = userRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf(Pair("error", "user not found")))

            userInDb.password = requestPassDto.password
            userRepository.save(userInDb)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("")
        } catch (e: Exception) {
            return ResponseEntity.internalServerError().body(mapOf(Pair("error", e.message.toString())))
        }

    }
}