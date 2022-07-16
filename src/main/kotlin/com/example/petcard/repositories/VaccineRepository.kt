package com.example.petcard.repositories

import com.example.petcard.model.Vaccine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VaccineRepository : JpaRepository<Vaccine, Long> {
    @Query("SELECT * FROM vaccine WHERE petId = ?1", nativeQuery = true)
    fun getByPetId(petId: Long): List<Vaccine>
}