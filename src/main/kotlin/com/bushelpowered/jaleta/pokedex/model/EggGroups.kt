package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.*

@Entity
@Table(name = "egg_groups", schema = "public")
data class EggGroups (

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "egg_group")
    val eggGroup: String
)

