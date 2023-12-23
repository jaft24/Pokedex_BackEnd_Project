package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.exception.PokemonNotFoundException
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
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

    fun combinedPokemonFilter(
        genus: String?,
        id: Int?,
        name: String?,
        page: Int,
        sort: String?,
        height: Double?,
        weight: Double?,
        type: String?,
        ability: String?,
        eggGroup: String?,
        pageable: Pageable
    ): Page<Pokemon> {

        val updatedPageable = updatePageable(pageable)

        val idPokemon = id?.takeIf { it != 0 }?.let {
            getAllPokemonByID(it)
        }
        val namePokemon = name?.takeIf { it != "" }?.let {
            getPokemonByName(it)
        }
        val genusPokemon = genus?.takeIf { it != "" }?.let {
            filterPokemonByGenus(it)
        }
        val heightPokemon = height?.takeIf { it != 0.0 }?.let {
            filterPokemonByHeight(it)
        }
        val weightPokemon = weight?.takeIf { it != 0.0 }?.let {
            filterPokemonByWeight(it)
        }
        val typePokemon = type?.takeIf { it != "" }?.let {
            filterPokemonByType(it)
        }
        val abilityPokemon = ability?.takeIf { it != "" }?.let {
            filterPokemonByAbility(it)
        }
        val eggGroupPokemon = eggGroup?.takeIf { it != "" }?.let {
            filterPokemonByEggGroup(it)
        }

        val allFiltersCombined: MutableList<Pokemon> = ArrayList(pokemonRepository.findAll())

        idPokemon?.let { allFiltersCombined.retainAll(idPokemon) }
        namePokemon?.let { allFiltersCombined.retainAll(namePokemon) }
        genusPokemon?.let { allFiltersCombined.retainAll(genusPokemon) }
        heightPokemon?.let { allFiltersCombined.retainAll(heightPokemon) }
        weightPokemon?.let { allFiltersCombined.retainAll(weightPokemon) }
        typePokemon?.let { allFiltersCombined.retainAll(typePokemon) }
        abilityPokemon?.let { allFiltersCombined.retainAll(abilityPokemon) }
        eggGroupPokemon?.let { allFiltersCombined.retainAll(eggGroupPokemon) }

        when (sort?.uppercase()) {
            "DESC" -> allFiltersCombined.sortByDescending { it.id }
            "A-Z" -> allFiltersCombined.sortBy { it.name }
            "Z-A" -> allFiltersCombined.sortByDescending { it.name }
            else -> allFiltersCombined.sortBy { it.id }
        }

        val startIndex = updatedPageable.offset.toInt()
        val endIndex = (startIndex + updatedPageable.pageSize).coerceAtMost(allFiltersCombined.size)
        val filteredResults = allFiltersCombined.subList(startIndex, endIndex)

        return PageImpl(filteredResults, updatedPageable, allFiltersCombined.size.toLong())
    }
}
