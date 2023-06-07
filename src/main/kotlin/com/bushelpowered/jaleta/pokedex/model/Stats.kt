package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id

@Entity
@Table(name = "stats", schema = "public")
data class Stats (

    @Id
    @Column(name = "pokemon_id")
    val pokemonId: Int,

    @Column(name = "hp")
    val hp: Int,

    @Column(name = "speed")
    val speed: Int,

    @Column(name = "attack")
    val attack: Int,

    @Column(name = "defense")
    val defense: Int,

    @Column(name = "special_attack")
    val specialAttack: Int,

    @Column(name = "special_defense")
    val specialDefense: Int

)