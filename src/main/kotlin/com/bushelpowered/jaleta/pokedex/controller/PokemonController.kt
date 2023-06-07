package com.bushelpowered.jaleta.pokedex.controller

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.service.PokemonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/api/pokemon")
class PokemonController(private var pokemonService: PokemonService) {

    @GetMapping("/all")
    fun getAllPokemon(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
       val allPokemons =  pokemonService.getAllPokemon(pageable)
        return ResponseEntity.ok(allPokemons)
    }

    @GetMapping("/byId/{id}")
    fun getPokemonById(@PathVariable id: Int): ResponseEntity<Pokemon> {
        val pokemon = pokemonService.getPokemonByID(id)
         return ResponseEntity.ok(pokemon)
    }

    @GetMapping("/byName/{name}")
    fun getPokemonByName(@PathVariable name: String): ResponseEntity<Pokemon> {
        val pokemon =  pokemonService.getPokemonByName(name)
         return ResponseEntity.ok(pokemon)
    }

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

    @GetMapping("/type/{typeName}")
    fun filterPokemonByTypes(@PathVariable typeName: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithType = pokemonService.filterPokemonByType(typeName, pageable)
         return ResponseEntity.ok(pokemonWithType)
    }

    @GetMapping("/ability/{abilityName}")
    fun filterPokemonByAbility(@PathVariable abilityName: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithAbility = pokemonService.filterPokemonByAbility(abilityName, pageable)
        return ResponseEntity.ok(pokemonWithAbility)
    }

    @GetMapping("/eggGroup/{eggGroupName}")
    fun filterPokemonByEggGroup(@PathVariable eggGroupName: String, pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val pokemonWithEggGroup = pokemonService.filterPokemonByEggGroup(eggGroupName, pageable)
        return ResponseEntity.ok(pokemonWithEggGroup)
    }

    @GetMapping("/filter")
    fun combinedFilters(
        @RequestParam(required = false) genus: String?,
        @RequestParam(required = false) height: Double?,
        @RequestParam(required = false) weight: Double?,
        @RequestParam(required = false) type: String?,
        @RequestParam(required = false) ability: String?,
        @RequestParam(required = false) eggGroup: String?,
        pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val filteredPokemons =  pokemonService.combinedPokemonFilter(genus, height, weight, type, ability, eggGroup, pageable)
        return ResponseEntity.ok(filteredPokemons)
    }

}