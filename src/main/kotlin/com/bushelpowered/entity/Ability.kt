package com.bushelpowered.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "abilities", schema = "public")
data class Ability(

    @Id
    val id: Int,

    val ability: String,

)
