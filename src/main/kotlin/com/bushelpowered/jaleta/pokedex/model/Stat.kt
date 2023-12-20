package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "stats", schema = "public")
data class Stat(

    @Id
    @Column(name = "id")
    val id: Int,

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
    val specialDefense: Int,

)
