package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "types", schema = "public")
data class Type(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "type")
    val type: String,

)
