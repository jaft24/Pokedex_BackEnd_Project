package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
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

    // Combined Filter (Name Sorting)
    @Query("SELECT p FROM Pokemon p WHERE (:id IS NULL OR p.id = :id) AND (:name IS NULL OR p.name = :name) AND " +
            "(:genus IS NULL OR p.genus = :genus) AND (:height IS NULL OR p.height = :height) AND " +
            "(:weight IS NULL OR p.weight = :weight) AND (:type IS NULL OR EXISTS (SELECT 1 FROM p.types t WHERE t.type = :type)) AND " +
            "(:ability IS NULL OR EXISTS (SELECT 1 FROM p.abilities a WHERE a.ability = :ability)) AND " +
            "(:eggGroup IS NULL OR EXISTS (SELECT 1 FROM p.eggGroups eg WHERE eg.eggGroup = :eggGroup)) " +
            "ORDER BY " +
            "CASE WHEN :adjustedSort = 'DESC' THEN p.name END DESC NULLS LAST, " +
            "p.name ASC NULLS LAST")
    fun findPokemonByCombinedFiltersNameSort(
            id: Int?,
            name: String?,
            genus: String?,
            height: Double?,
            weight: Double?,
            type: String?,
            ability: String?,
            eggGroup: String?,
            adjustedSort: String?,
            pageable: Pageable
    ): Page<Pokemon>

    // Combined Filter (ID Sorting - Default)
    @Query("SELECT p FROM Pokemon p WHERE (:id IS NULL OR p.id = :id) AND (:name IS NULL OR p.name = :name) AND " +
            "(:genus IS NULL OR p.genus = :genus) AND (:height IS NULL OR p.height = :height) AND " +
            "(:weight IS NULL OR p.weight = :weight) AND (:type IS NULL OR EXISTS (SELECT 1 FROM p.types t WHERE t.type = :type)) AND " +
            "(:ability IS NULL OR EXISTS (SELECT 1 FROM p.abilities a WHERE a.ability = :ability)) AND " +
            "(:eggGroup IS NULL OR EXISTS (SELECT 1 FROM p.eggGroups eg WHERE eg.eggGroup = :eggGroup)) " +
            "ORDER BY " +
            "CASE WHEN :adjustedSort = 'DESC' THEN p.id END DESC NULLS LAST, " +
            "p.id ASC NULLS LAST")
    fun findPokemonByCombinedFiltersIdSort(
            id: Int?,
            name: String?,
            genus: String?,
            height: Double?,
            weight: Double?,
            type: String?,
            ability: String?,
            eggGroup: String?,
            adjustedSort: String?,
            pageable: Pageable
    ): Page<Pokemon>
}
