package com.bushelpowered.jaleta.pokedex.controller

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.service.PokemonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.transaction.Transactional

@RestController
@Transactional
@CrossOrigin
@RequestMapping("/api/pokemon")
class PokemonController(private var pokemonService: PokemonService) {

    @GetMapping("/all")
    fun getAllPokemon(pageable: Pageable): ResponseEntity<Page<Pokemon>> {
        val allPokemon = pokemonService.getAllPokemon(pageable)
        return ResponseEntity.ok(allPokemon)
    }

    @GetMapping("/byId/{id}")
    fun getPokemonById(@PathVariable id: Int): ResponseEntity<Optional<Pokemon>> {
        val pokemon = pokemonService.getPokemonByID(id)
        return ResponseEntity.ok(pokemon)
    }

    @GetMapping("/filter")
    fun combinedFilters(
            @RequestParam(required = false) genus: String?,
            @RequestParam(required = false) id: Int?,
            @RequestParam(required = false) name: String?,
            @RequestParam(required = false) height: Double?,
            @RequestParam(required = false) weight: Double?,
            @RequestParam(required = false) type: String?,
            @RequestParam(required = false) ability: String?,
            @RequestParam(required = false) eggGroup: String?,
            @RequestParam(required = false) sort: String?,
            @RequestParam(required = false, defaultValue = "0") page: Int,
            pageable: Pageable): ResponseEntity<Page<Pokemon>> {

        val resultPage: Page<Pokemon> = pokemonService.combinedPokemonFilter(genus, id, name, page, sort, height, weight, type, ability, eggGroup, pageable)
        return ResponseEntity.ok(resultPage)
    }


    // Test APIs
    @GetMapping("/byName/{name}")
    fun getPokemonByName(@PathVariable name: String, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemon = pokemonService.getPokemonByName(name)
        return ResponseEntity.ok(pokemon)
    }

    @GetMapping("/byGenus/{genus}")
    fun filterByGenus(@PathVariable genus: String, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByGenus(genus)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byHeight/{height}")
    fun filterByHeight(@PathVariable height: Double, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByHeight(height)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byWeight/{weight}")
    fun filterByWeight(@PathVariable weight: Double, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemons = pokemonService.filterPokemonByWeight(weight)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/byType/{type}")
    fun filterPokemonByType(@PathVariable type: String, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemonWithType = pokemonService.filterPokemonByType(type)
        return ResponseEntity.ok(pokemonWithType)
    }

    @GetMapping("/byAbility/{ability}")
    fun filterPokemonByAbility(@PathVariable ability: String, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemonWithAbility = pokemonService.filterPokemonByAbility(ability)
        return ResponseEntity.ok(pokemonWithAbility)
    }

    @GetMapping("/byEggGroup/{eggGroup}")
    fun filterPokemonByEggGroup(@PathVariable eggGroup: String, pageable: Pageable): ResponseEntity<List<Pokemon>> {
        val pokemonWithEggGroup = pokemonService.filterPokemonByEggGroup(eggGroup)
        return ResponseEntity.ok(pokemonWithEggGroup)
    }

}
