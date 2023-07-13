package com.bushelpowered.controller

import com.bushelpowered.entity.Pokemon
import com.bushelpowered.service.PokemonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/api/pokemon")
class PokemonController(private var pokemonService: PokemonService) {

    @GetMapping("/all")
    fun getAllPokemon(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val allPokemons = pokemonService.getAllPokemon(pageable)
        return ResponseEntity.ok(allPokemons)
    }

    @GetMapping("/byId/{id}")
    fun getPokemonById(@PathVariable id: Int): ResponseEntity<Pokemon> {
        val pokemon = pokemonService.getPokemonByID(id)
        return ResponseEntity.ok(pokemon)
    }

    @GetMapping("/byName/{name}")
    fun getPokemonByName(@PathVariable name: String): ResponseEntity<Pokemon> {
        val pokemon = pokemonService.getPokemonByName(name)
        return ResponseEntity.ok(pokemon)
    }

    @GetMapping("/allNames")
    fun getAllPokemonNames(): ResponseEntity<MutableList<String>> {
        val pokemonNames = pokemonService.getAllPokemonNames()
        return ResponseEntity.ok(pokemonNames)
    }

//    @GetMapping("/allByName")
//    fun sortPokemonByAlphabet(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
//        val allPokemonsByName = pokemonService.sortPokemonByAlphabet(pageable)
//        return ResponseEntity.ok(allPokemonsByName)
//    }
//
//    @GetMapping("/allByNameRev")
//    fun sortPokemonByAlphabetRev(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
//        val allPokemonsByNameRev = pokemonService.sortPokemonByAlphabetRev(pageable)
//        return ResponseEntity.ok(allPokemonsByNameRev)
//    }
//
//    @GetMapping("/allRev")
//    fun sortPokemonByIdRev(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
//        val allPokemonsByIdRev = pokemonService.sortPokemonByIdRev(pageable)
//        return ResponseEntity.ok(allPokemonsByIdRev)
//    }

    @GetMapping("/byGenus/{genus}")
    fun filterByGenus(@PathVariable genus: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByGenus(genus, pageable)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byHeight/{height}")
    fun filterByHeight(@PathVariable height: Double, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByHeight(height, pageable)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byWeight/{weight}")
    fun filterByWeight(@PathVariable weight: Double, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByWeight(weight, pageable)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byType/{type}")
    fun filterPokemonByType(@PathVariable type: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithType = pokemonService.filterPokemonByType(type, pageable)
        return ResponseEntity.ok(pokemonWithType)
    }

    @GetMapping("/byAbility/{ability}")
    fun filterPokemonByAbility(@PathVariable ability: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithAbility = pokemonService.filterPokemonByAbility(ability, pageable)
        return ResponseEntity.ok(pokemonWithAbility)
    }

    @GetMapping("/byEggGroup/{eggGroup}")
    fun filterPokemonByEggGroup(@PathVariable eggGroup: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithEggGroup = pokemonService.filterPokemonByEggGroup(eggGroup, pageable)
        return ResponseEntity.ok(pokemonWithEggGroup)
    }

    @GetMapping("/filter")
    fun combinedFilters(
        @RequestParam(required = false) id: Int?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) sort: String?,
        @RequestParam(required = false) genus: String?,
        @RequestParam(required = false) height: Double?,
        @RequestParam(required = false) weight: Double?,
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) ability: String?,
        @RequestParam(required = false) eggGroup: String?,
        pageable: Pageable,
    ): ResponseEntity<Page<Pokemon>> {
        val filteredPokemons = pokemonService.combinedPokemonFilter(id, name, sort, genus, height, weight, type, ability, eggGroup, pageable)
        return ResponseEntity.ok(filteredPokemons)
    }
}
