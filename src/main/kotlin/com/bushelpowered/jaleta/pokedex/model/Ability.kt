package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id

@Entity
@Table(name = "abilities", schema = "public")
data class Ability (

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "ability")
    val ability: String

)