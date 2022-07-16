package com.example.petcard.controllers

import com.example.petcard.controllers.dto.VaccineInsertDto
import com.example.petcard.model.Vaccine
import com.example.petcard.repositories.PetRepository
import com.example.petcard.repositories.VaccineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/vaccines")
class VaccineController {
    @Autowired
    private lateinit var vaccineRepository: VaccineRepository

    @Autowired
    private lateinit var petRepository: PetRepository

    @GetMapping
    fun getAll(): Any = ResponseEntity.ok(petRepository.findAll())

    @GetMapping("from-pet/{id}")
    fun getByPetId(@PathVariable("id") id: Long): ResponseEntity<Any> {
        val pet = petRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "pet not found")))

        val vaccines = vaccineRepository.getByPetId(pet.id)

        return ResponseEntity.ok(vaccines)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): Any {
        val vaccine = vaccineRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "vaccine not found")))

        return ResponseEntity.ok(vaccine)
    }

    @PostMapping
    fun save(@RequestBody request: VaccineInsertDto): Any {
        try {
            val pet =
                petRepository.findByIdOrNull(request.petId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf(Pair("error", "pet not found")))

            val vaccine = Vaccine()
            vaccine.name = request.name
            vaccine.firstVaccine = request.firstVaccine
            vaccine.pet = pet

            vaccineRepository.save(vaccine)

            return ResponseEntity.ok(vaccine)
        } catch (ex: java.lang.IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf(Pair("error", "Tipo inválido")))
        }

    }


    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): Any {
        val vaccine = vaccineRepository.findByIdOrNull(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf(Pair("error", "pet not found")))

        vaccineRepository.delete(vaccine)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("")
    }
}