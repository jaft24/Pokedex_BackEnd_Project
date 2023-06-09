package com.bushelpowered.jaleta.pokedex.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "egg_groups", schema = "public")
data class EggGroup(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "egg_group")
    val eggGroup: String,

)
