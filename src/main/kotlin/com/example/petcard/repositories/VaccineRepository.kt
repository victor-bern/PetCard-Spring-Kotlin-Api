package com.example.petcard.repositories

import com.example.petcard.model.Vaccine
import org.springframework.data.jpa.repository.JpaRepository

interface VaccineRepository : JpaRepository<Vaccine, Long>