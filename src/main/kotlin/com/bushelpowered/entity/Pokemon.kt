package com.bushelpowered.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.PrimaryKeyJoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "pokemon", schema = "public")
data class Pokemon(

    @Id
    val id: Int,

    val name: String,

    @OneToMany
    @JoinTable(
        name = "pokemon_types",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "type_id")],
    )
    val types: List<Type> = emptyList(),

    val height: Double,

    val weight: Double,

    @OneToMany
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "ability_id")],
    )
    val abilities: List<Ability> = emptyList(),

    @OneToMany
    @JoinTable(
        name = "pokemon_egg_groups",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "egg_group_id")],
    )
    val eggGroups: List<EggGroup> = emptyList(),

    @OneToOne
    @PrimaryKeyJoinColumn
    val stat: Stat,

    val genus: String,

    val description: String,

    )
