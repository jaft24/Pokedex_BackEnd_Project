package com.bushelpowered.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "egg_groups", schema = "public")
data class EggGroup(

    @Id
    val id: Int,

    val eggGroup: String,

)
