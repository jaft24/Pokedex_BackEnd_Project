package com.bushelpowered.jaleta.pokedex.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigInteger

@Entity
    @Table(name = "pokemon", schema = "public")
    data class Pokemon(

    @Id
    @Column(name = "id")
    val id: Int,

    @Column(name = "name")
    val name: String,

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
            name = "pokemon_types",
            joinColumns = [JoinColumn(name = "pokemon_id")],
            inverseJoinColumns = [JoinColumn(name = "type_id")]
        )
    val types: List<Types> = emptyList(),

    @Column(name = "height")
    val height: Float,

    @Column(name = "weight")
    val weight: Float,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "ability_id")]
    )
    val abilities: List<Abilities> = emptyList(),

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pokemon_egg_groups",
        joinColumns = [JoinColumn(name = "pokemon_id")],
        inverseJoinColumns = [JoinColumn(name = "egg_group_id")]
    )
    val eggGroups: List<EggGroups> = emptyList(),

    @JsonManagedReference
    @OneToOne(mappedBy = "pokemon")
    @PrimaryKeyJoinColumn
    val stats: Stats,

    @Column(name = "genus")
    val genus: String,

    @Column(name = "description")
    val description: String
    )

