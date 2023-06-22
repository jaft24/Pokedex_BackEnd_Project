package com.bushelpowered.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "captured_pokemon", schema = "public")
data class Capture(

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID = UUID.randomUUID(),

    var trainerId: String = "",

    var pokemonId: Int = 0,

)
