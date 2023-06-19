package com.bushelpowered.service

import com.bushelpowered.exception.PokemonNotFoundException
import com.bushelpowered.entity.Pokemon
import com.bushelpowered.repository.PokemonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

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

    fun getPokemonByID(id: Int): Pokemon {
        if (!pokemonRepository.existsById(id)) {
            throw PokemonNotFoundException("getPokemonByID")
        }
        return pokemonRepository.findAllById(id)
    }

    fun getPokemonByName(name: String): Pokemon {
        if (!pokemonRepository.existsByName(name)) {
            throw PokemonNotFoundException("getPokemonByName")
        }
        return pokemonRepository.findByName(name)
    }

    fun filterPokemonByHeight(height: Double, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findByHeight(height, updatedPageable)
    }

    fun filterPokemonByWeight(weight: Double, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findByWeight(weight, updatedPageable)
    }

    fun filterPokemonByGenus(genusName: String, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findPokemonsByGenus(genusName, updatedPageable)
    }

    fun filterPokemonByType(typeName: String, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findPokemonByTypesType(typeName, updatedPageable)
    }

    fun filterPokemonByAbility(abilityName: String, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findPokemonByAbilitiesAbility(abilityName, updatedPageable)
    }

    fun filterPokemonByEggGroup(eggGroupName: String, pageable: Pageable): Page<Pokemon> {
        val updatedPageable = updatePageable(pageable)
        return pokemonRepository.findPokemonByEggGroupsEggGroup(eggGroupName, updatedPageable)
    }

    fun combinedPokemonFilter(
        genus: String?,
        height: Double?,
        weight: Double?,
        type: String?,
        ability: String?,
        eggGroup: String?,
        pageable: Pageable,
    ): PageImpl<Pokemon> {
        val updatedPageable = updatePageable(pageable)

        val genusPokemon = genus?.let {
            filterPokemonByGenus(it, updatedPageable)
        }
        val heightPokemon = height?.let {
            filterPokemonByHeight(it, updatedPageable)
        }
        val weightPokemon = weight?.let {
            filterPokemonByWeight(it, updatedPageable)
        }
        val typePokemon = type?.let {
            filterPokemonByType(it, updatedPageable)
        }
        val abilityPokemon = ability?.let {
            filterPokemonByAbility(it, updatedPageable)
        }
        val eggGroupPokemon = eggGroup?.let {
            filterPokemonByEggGroup(it, updatedPageable)
        }

        val allFiltersCombined: MutableList<com.bushelpowered.entity.Pokemon> = mutableListOf()
        allFiltersCombined.addAll(pokemonRepository.findAll())
        genusPokemon?.let { allFiltersCombined.retainAll(it.content) }
        heightPokemon?.let { allFiltersCombined.retainAll(it.content) }
        weightPokemon?.let { allFiltersCombined.retainAll(it.content) }
        typePokemon?.let { allFiltersCombined.retainAll(it.content) }
        abilityPokemon?.let { allFiltersCombined.retainAll(it.content) }
        eggGroupPokemon?.let { allFiltersCombined.retainAll(it.content) }

        val startIndex = updatedPageable.offset.toInt()
        val endIndex = (startIndex + updatedPageable.pageSize).coerceAtMost(allFiltersCombined.size)
        val filteredResults = allFiltersCombined.subList(startIndex, endIndex)

        return PageImpl(filteredResults, updatedPageable, allFiltersCombined.size.toLong())
    }
}
