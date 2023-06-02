package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.*

@Entity
@Table(name = "abilities", schema = "public")

data class Abilities (
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "ability")
    val ability: String,

)

