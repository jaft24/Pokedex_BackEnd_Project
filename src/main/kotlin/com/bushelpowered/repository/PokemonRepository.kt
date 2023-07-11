package com.bushelpowered.repository

import com.bushelpowered.entity.Pokemon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository : JpaRepository<Pokemon, Int> {
    fun findAllByOrderByNameAsc(): MutableList<Pokemon>
    fun findAllByOrderByNameDesc(): MutableList<Pokemon>
    fun findAllByOrderByIdDesc(): MutableList<Pokemon>
    fun findByName(name: String): Pokemon
    fun findAllById(id: Int): Pokemon
    fun existsByName(name: String): Boolean
    fun findPokemonsByGenus(genus: String?): List<Pokemon>
    fun findPokemonsByGenus(genus: String, pageable: Pageable?): Page<Pokemon>
    fun findByWeight(weight: Double, pageable: Pageable): Page<Pokemon>
    fun findByWeight(weight: Double?): List<Pokemon>
    fun findByHeight(height: Double, pageable: Pageable): Page<Pokemon>
    fun findByHeight(height: Double?): List<Pokemon>
    fun findPokemonByTypesType(typeName: String, pageable: Pageable): Page<Pokemon>
    fun findPokemonByTypesType(typeName: String?): List<Pokemon>
    fun findPokemonByAbilitiesAbility(abilityName: String, pageable: Pageable): Page<Pokemon>
    fun findPokemonByAbilitiesAbility(abilityName: String?): List<Pokemon>
    fun findPokemonByEggGroupsEggGroup(eggGroupName: String, pageable: Pageable): Page<Pokemon>
    fun findPokemonByEggGroupsEggGroup(eggGroupName: String?): List<Pokemon>
}
