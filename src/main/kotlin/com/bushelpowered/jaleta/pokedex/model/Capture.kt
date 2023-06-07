package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.stereotype.Component

@Component
@Entity
@Table(name = "captured", schema = "public")
data class Capture (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0,

    @Column(name = "trainer_id")
    var trainerId: String = "",

    @Column(name = "pokemon_id")
    var pokemonId: Int = 0

)