package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "egg_groups", schema = "public")
data class EggGroup(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "egg_group")
    val eggGroup: String,

)
