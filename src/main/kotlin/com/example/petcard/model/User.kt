package com.example.petcard.model

import com.example.petcard.services.BcryptService
import javax.persistence.*


@Entity
@Table(name = "user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    var name: String = ""


    @Column(name = "email", unique = true, nullable = false)
    var email: String = ""

    @Column(name = "password", nullable = false)
    var password: String = ""
        set(value) {
            val passHashed = BcryptService.hashPassword(value)
            field = passHashed
        }


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = false)
    var pets: List<Pet> = listOf()


}