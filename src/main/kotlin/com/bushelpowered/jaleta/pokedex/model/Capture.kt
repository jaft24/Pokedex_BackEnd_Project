package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "captured_pokemon", schema = "public")
data class Capture(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int = 0,

    @Column(name = "trainer_id")
    var trainerId: String = "",

    @Column(name = "pokemon_id")
    var pokemonId: Int = 0,

)
