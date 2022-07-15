package com.example.petcard.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "vaccine")
class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "firstVaccine", nullable = false)
    var firstVaccine: Date = Date()
        set(value) {
            field = value
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.YEAR, 1)
            nextVaccine = calendar.time
        }

    @Column(name = "nextVaccine", nullable = false)
    var nextVaccine: Date = Date()

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    lateinit var pet: Pet
}