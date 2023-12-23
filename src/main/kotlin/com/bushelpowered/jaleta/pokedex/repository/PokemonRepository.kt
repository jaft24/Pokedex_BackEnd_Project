package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<Pokemon, Int> {
    fun existsByName(name: String): Boolean

    // Filter Queries
    fun findByName(name: String): List<Pokemon>
    fun findAllById(id: Int): List<Pokemon>
    fun findPokemonByGenus(genus: String): List<Pokemon>
    fun findByHeight(height: Double): List<Pokemon>
    fun findByWeight(weight: Double): List<Pokemon>
    fun findPokemonByTypesType(typeName: String): List<Pokemon>
    fun findPokemonByAbilitiesAbility(abilityName: String): List<Pokemon>
    fun findPokemonByEggGroupsEggGroup(eggGroupName: String): List<Pokemon>
}
