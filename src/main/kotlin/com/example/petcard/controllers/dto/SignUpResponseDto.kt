package com.example.petcard.controllers.dto

import com.example.petcard.model.Pet

class SignUpResponseDto(val id: Long, val email: String, pets: List<Pet>)