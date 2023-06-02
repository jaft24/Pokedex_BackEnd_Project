package com.bushelpowered.jaleta.pokedex.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "types", schema = "public")
data class Types (
    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "type")
    val type: String,

    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "types")
    private val pokemon: List<Pokemon> = emptyList()


)

