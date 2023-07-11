package com.bushelpowered.service

import com.bushelpowered.entity.Pokemon
import com.bushelpowered.exception.PokemonNotFoundException
import com.bushelpowered.repository.PokemonRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.math.min

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

    fun getAllPokemonNames(): MutableList<String> {
        val pokemonList = pokemonRepository.findAll()
        val pokemonNames = mutableListOf<String>()

        for (pokemon in pokemonList) {
            pokemonNames.add(pokemon.name)
        }

        return pokemonNames
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
        return pokemonRepository.findByName((name.substring(0, 1).uppercase() + name.substring(1)))
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
        id: String?,
        name: String?,
        sort: String?,
        genus: String?,
        height: Double?,
        weight: Double?,
        type: String?,
        ability: String?,
        eggGroup: String?,
        pageable: Pageable,
    ): PageImpl<Pokemon> {
        val updatedPageable = updatePageable(pageable)

        val singlePokemonName = name?.takeIf { it.isNotEmpty() }?.let {
            if (!pokemonRepository.existsByName(name)) {
                throw PokemonNotFoundException("getPokemonByName")
            }
            pokemonRepository.findByName((name.substring(0, 1).uppercase() + name.substring(1)))
        }

        val singlePokemonId = id?.takeIf { it.isNotEmpty() }?.let {
            if (!pokemonRepository.existsById(id.toInt())) {
                throw PokemonNotFoundException("getPokemonByID")
            }
             pokemonRepository.findAllById(id.toInt())
        }

        val heightPokemon = height?.let {
            pokemonRepository.findByHeight(height)
        }
        val weightPokemon = weight?.let {
            pokemonRepository.findByWeight(weight)
        }
        val genusPokemon = genus?.takeIf { it.isNotEmpty() }?.let {
            pokemonRepository.findPokemonsByGenus(genus)
        }
        val typePokemon = type?.takeIf { it.isNotEmpty() }?.let {
            pokemonRepository.findPokemonByTypesType(type)
        }
        val abilityPokemon = ability?.takeIf { it.isNotEmpty() }?.let {
            pokemonRepository.findPokemonByAbilitiesAbility(ability)
        }
        val eggGroupPokemon = eggGroup?.takeIf { it.isNotEmpty() }?.let {
            pokemonRepository.findPokemonByEggGroupsEggGroup(eggGroup)
        }


        var allFiltersCombined: MutableList<Pokemon> = pokemonRepository.findAll()

        var sortedPokemon: MutableList<Pokemon>? = sort?.takeIf { it.isNotEmpty() }?.let {
            when (it) {
                "A" -> pokemonRepository.findAllByOrderByNameAsc()
                "Z" -> pokemonRepository.findAllByOrderByNameDesc()
                "-1" -> pokemonRepository.findAllByOrderByIdDesc()
                else -> null // Handle invalid sort input
            }
        }


        if (!sortedPokemon.isNullOrEmpty()) {
            allFiltersCombined = sortedPokemon
        }

        genusPokemon?.let { allFiltersCombined.retainAll(it) }
        heightPokemon?.let { allFiltersCombined.retainAll(it) }
        weightPokemon?.let { allFiltersCombined.retainAll(it) }
        typePokemon?.let { allFiltersCombined.retainAll(it) }
        abilityPokemon?.let { allFiltersCombined.retainAll(it) }
        eggGroupPokemon?.let { allFiltersCombined.retainAll(it) }

        if (singlePokemonName != null) {
            if (!singlePokemonName.equals(null)) {
                allFiltersCombined = mutableListOf(singlePokemonName)
            }
        } else if (singlePokemonId != null) {
            if (!singlePokemonId.equals(null)) {
                allFiltersCombined = mutableListOf(singlePokemonId)
            }
        }

        val start: Int = updatedPageable.offset.toInt()
        val end: Int = min((start + updatedPageable.pageSize), allFiltersCombined.size)
        val pageContent: List<Pokemon> = allFiltersCombined.subList(start, end)

        return PageImpl(pageContent, updatedPageable, allFiltersCombined.size.toLong())
    }
}
