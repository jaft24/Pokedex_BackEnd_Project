package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<Pokemon, Int> {

    fun findByName(name: String): Pokemon
    fun findAllById(id: Int): Pokemon
    fun existsByName(name: String): Boolean
    fun findPokemonsByGenus(genus: String, pageable: Pageable): Page<Pokemon>
    fun findByHeight(height: Double, pageable: Pageable): Page<Pokemon>
    fun findByWeight(weight: Double, pageable: Pageable): Page<Pokemon>
    fun findPokemonByTypesType(typeName: String, pageable: Pageable): Page<Pokemon>
    fun findPokemonByAbilitiesAbility(abilityName: String, pageable: Pageable): Page<Pokemon>
    fun findPokemonByEggGroupsEggGroup(eggGroupName: String, pageable: Pageable): Page<Pokemon>
}
