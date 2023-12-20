package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Entity

@Entity
@Table(name = "abilities", schema = "public")
data class Ability(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "ability")
    val ability: String,

)
