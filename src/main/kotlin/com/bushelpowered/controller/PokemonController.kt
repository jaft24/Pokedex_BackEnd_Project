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
