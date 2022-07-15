package com.example.petcard.controllers

import com.example.petcard.controllers.dto.PetInsertDto
import com.example.petcard.controllers.dto.PetUpdateDto
import com.example.petcard.model.AnimalType
import com.example.petcard.model.Pet
import com.example.petcard.repositories.PetRepository
import com.example.petcard.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/v1/pets")
class PetController {
    @Autowired
    private lateinit var petRepository: PetRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping
    fun getAll(): Any = ResponseEntity.ok(petRepository.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): Any {
        val pet = petRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "pet not found")))

        return ResponseEntity.ok(pet)
    }

    @PostMapping
    fun save(@RequestBody request: PetInsertDto): Any {
        try {
            val user =
                userRepository.findByIdOrNull(request.userId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf(Pair("error", "user not found")))

            val pet = Pet()
            pet.name = request.name
            pet.race = request.race
            pet.animalType = AnimalType.valueOf(request.animalType)
            pet.user = user

            petRepository.save(pet)

            return ResponseEntity.ok(pet)
        } catch (ex: java.lang.IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf(Pair("error", "Tipo inválido")))
        }

    }

    @PutMapping("/{id}")
    fun update(@RequestBody request: PetUpdateDto, @PathVariable("id") id: Long): Any {
        val pet = petRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "pet not found")))

        pet.name = request.name
        pet.race = request.race
        petRepository.save(pet)

        return ResponseEntity.status(HttpStatus.OK).body("")
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): Any {
        val pet = petRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "pet not found")))

        petRepository.delete(pet)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("")
    }

}