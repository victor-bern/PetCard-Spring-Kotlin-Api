package com.example.petcard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetcardApplication


fun main(args: Array<String>) {
    runApplication<PetcardApplication>(*args)
}
