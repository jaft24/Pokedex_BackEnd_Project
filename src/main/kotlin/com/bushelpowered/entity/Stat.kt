package com.bushelpowered.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "stats", schema = "public")
data class Stat(

    @Id
    val id: Int,

    val hp: Int,

    val speed: Int,

    val attack: Int,

    val defense: Int,

    val specialAttack: Int,

    val specialDefense: Int,

)
