package com.bushelpowered.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "types", schema = "public")
data class Type(

    @Id
    val id: Int,

    val type: String,

)
