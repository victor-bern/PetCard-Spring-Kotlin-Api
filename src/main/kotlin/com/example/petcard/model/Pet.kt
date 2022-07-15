package com.example.petcard.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "pet")
class Pet : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "race", nullable = false)
    var race: String = ""

    @Column(name = "animalType", nullable = false)
    var animalType: AnimalType = AnimalType.DOG

    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = false)
    var vaccines: List<Vaccine> = listOf()

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    @JsonIgnore
    lateinit var user: User
}