package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "types", schema = "public")
data class Type(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "type")
    val type: String,

)
