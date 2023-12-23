package com.bushelpowered.jaleta.pokedex.model

import javax.persistence.*

@Entity
@Table(name = "pokemon", schema = "public")
data class Pokemon(

        @Id
        @Column(name = "id")
        val id: Int,

        @Column(name = "name")
        val name: String,

        @OneToMany
        @JoinTable(
                name = "pokemon_types",
                schema = "public",
                joinColumns = [JoinColumn(name = "pokemon_id")],
                inverseJoinColumns = [JoinColumn(name = "type_id")],
        )
        val types: List<Type> = emptyList(),

        @Column(name = "height")
        val height: Double,

        @Column(name = "weight")
        val weight: Double,

        @OneToMany
        @JoinTable(
                schema = "public",
                name = "pokemon_abilities",
                joinColumns = [JoinColumn(name = "pokemon_id")],
                inverseJoinColumns = [JoinColumn(name = "ability_id")],
        )
        val abilities: List<Ability> = emptyList(),

        @OneToMany
        @JoinTable(
                name = "pokemon_egg_groups",
                schema = "public",
                joinColumns = [JoinColumn(name = "pokemon_id")],
                inverseJoinColumns = [JoinColumn(name = "egg_group_id")],
        )
        val eggGroups: List<EggGroup> = emptyList(),

        @OneToOne
        @PrimaryKeyJoinColumn
        val stat: Stat,

        @Column(name = "genus")
        val genus: String,

        @Column(name = "description")
        val description: String,

        )
