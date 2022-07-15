package com.example.petcard.repositories

import com.example.petcard.model.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Qualifier("users")
@Repository
interface UserRepository : JpaRepository<User, Long> {

    @Query("SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    fun findByEmail(email: String): User?
}