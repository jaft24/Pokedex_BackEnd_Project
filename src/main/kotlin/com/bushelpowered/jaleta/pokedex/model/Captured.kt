package com.bushelpowered.jaleta.pokedex.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "captured", schema = "public")
data class Captured (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int= 0,

    @Column(name = "trainer_id")
    var trainerId: String,

    @Column(name = "pokemon_id")
    var pokemonId: Int,

    )








//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pokemon_id")
//    val pokemon: List<Pokemon> = emptyList()

//    val capturedPokemons: List<Pokemon> = emptyList()


