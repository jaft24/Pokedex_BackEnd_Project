package com.bushelpowered.jaleta.pokedex.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.math.BigInteger

@Entity
@Table(name = "stats", schema = "public")
data class Stats (

    @Id
    @Column(name = "pokemon_id")
    val pokemon_id: Int,

    @Column(name = "hp")
    val hp: Int,

    @Column(name = "speed")
    val speed: Int,

    @Column(name = "attack")
    val attack: Int,

    @Column(name = "defense")
    val defense: Int,

    @Column(name = "special_attack")
    val special_attack: Int,

    @Column(name = "special_defense")
    val special_defense: Int,

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "pokemon_id")
    val pokemon: Pokemon



)

