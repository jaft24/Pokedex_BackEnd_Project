package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.exception.PokemonNotFoundException
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.*


@Service
class PokemonService(private var pokemonRepository: PokemonRepository) {

    private final fun updatePageable(pageable: Pageable): Pageable {
        val fixedPageSize = 10
        return PageRequest.of(pageable.pageNumber, fixedPageSize, pageable.sort)
    }

    fun getAllPokemon(pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findAll(updatedPageable)
    }
    fun getPokemonByID(id: Int): Optional<Pokemon> {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException("There is no Pokemon with this Id.")
        }
        return pokemonRepository.findById(id)
    }

    fun getAllPokemonByID(id: Int): List<Pokemon> {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException("There is no Pokemon with this Id.")
        }
        return pokemonRepository.findAllById(id)
    }
    fun getPokemonByName(name: String): List<Pokemon> {
        if (!pokemonRepository.existsByName(name)) {
            throw PokemonNotFoundException("There is no Pokemon with this Name.")
        }
        return pokemonRepository.findByName(name)
    }
    fun filterPokemonByHeight(height: Double): List<Pokemon> {
        return pokemonRepository.findByHeight(height)
    }
    fun filterPokemonByWeight(weight: Double): List<Pokemon> {
        return pokemonRepository.findByWeight(weight)
    }
    fun filterPokemonByGenus(genusName: String): List<Pokemon> {
        return pokemonRepository.findPokemonByGenus(genusName)
    }
    fun filterPokemonByType(typeName: String): List<Pokemon> {
        return pokemonRepository.findPokemonByTypesType(typeName)
    }
    fun filterPokemonByAbility(abilityName: String): List<Pokemon> {
        return pokemonRepository.findPokemonByAbilitiesAbility(abilityName)
    }
    fun filterPokemonByEggGroup(eggGroupName: String): List<Pokemon> {
        return pokemonRepository.findPokemonByEggGroupsEggGroup(eggGroupName)
    }

    fun sortHandler(sort: String?): Pair<String, String>{
        val adjustedSort = sort?.uppercase()
        var sortOn = "id"
        var sortOrder = "ASC"
        if (adjustedSort == "DESC"){
            sortOrder = "DESC"
            return Pair(sortOn, sortOrder)
        } else if (adjustedSort == "A-Z") {
            sortOn = "name"
            return Pair(sortOn, sortOrder)
        } else if (adjustedSort == "Z-A") {
            sortOn = "name"
            sortOrder = "DESC"
            return Pair(sortOn, sortOrder)
        } else return Pair(sortOn, sortOrder)

    }

    @Cacheable("combinedPokemonFilter", keyGenerator = "customKeyGenerator")
    fun combinedPokemonFilter(
            genus: String?,
            id: Int?,
            name: String?,
            page: Int,
            sortOrder: String?,
            height: Double?,
            weight: Double?,
            type: String?,
            ability: String?,
            eggGroup: String?,
            pageable: Pageable
    ): Page<Pokemon> {

        val updatedPageable = updatePageable(pageable)
        val sortResult = sortHandler(sortOrder)

        // Convert empty string or 0 to null
        val adjustedGenus = if (genus.isNullOrBlank()) null else genus
        val adjustedId = if (id == 0) null else id
        val adjustedName = if (name.isNullOrBlank()) null else name
        val sortBy = sortResult.first
        val adjustedSort = if (sortResult.second == "ASC") null else "DESC"
        val adjustedHeight = if (height == 0.0) null else height
        val adjustedWeight = if (weight == 0.0) null else weight
        val adjustedType = if (type.isNullOrBlank()) null else type
        val adjustedAbility = if (ability.isNullOrBlank()) null else ability
        val adjustedEggGroup = if (eggGroup.isNullOrBlank()) null else eggGroup


        val filteredPokemon = if (sortBy == "id") {
            pokemonRepository.findPokemonByCombinedFiltersIdSort(
                    adjustedId, adjustedName, adjustedGenus, adjustedHeight, adjustedWeight, adjustedType, adjustedAbility, adjustedEggGroup, adjustedSort, updatedPageable
            )
        } else {
            pokemonRepository.findPokemonByCombinedFiltersNameSort(
                    adjustedId, adjustedName, adjustedGenus, adjustedHeight, adjustedWeight, adjustedType, adjustedAbility, adjustedEggGroup, adjustedSort, updatedPageable
            )
        }

        return PageImpl(filteredPokemon.content, updatedPageable, filteredPokemon.totalElements)
    }

    fun customKeyGenerator(
            genus: String?,
            id: Int?,
            name: String?,
            page: Int,
            sortOrder: String?,
            height: Double?,
            weight: Double?,
            type: String?,
            ability: String?,
            eggGroup: String?,
            pageable: Pageable
    ): String {
        return "combinedPokemonFilter_" +
                "${genus ?: ""}_${id}_${name ?: ""}_${page}_${sortOrder ?: ""}_${height?.toString() ?: ""}_${weight?.toString() ?: ""}_${type ?: ""}_${ability ?: ""}_${eggGroup ?: ""}" +
                "_${pageable.pageNumber}_${pageable.pageSize}"
    }
}
