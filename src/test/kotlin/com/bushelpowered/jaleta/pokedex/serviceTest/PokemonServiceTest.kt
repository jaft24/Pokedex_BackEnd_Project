package com.bushelpowered.jaleta.pokedex.serviceTest

import com.bushelpowered.jaleta.pokedex.exception.PokemonNotFoundException
import com.bushelpowered.jaleta.pokedex.model.Ability
import com.bushelpowered.jaleta.pokedex.model.EggGroup
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.model.Stat
import com.bushelpowered.jaleta.pokedex.model.Type
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import com.bushelpowered.jaleta.pokedex.service.PokemonService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

class PokemonServiceTest {

    @Mock
    private lateinit var pokemonRepository: PokemonRepository

    @InjectMocks
    private lateinit var pokemonService: PokemonService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getAllPokemon_ShouldReturnAllPokemon() {
        val pageable = PageRequest.of(0, 10)
        val pokemonList = listOf(
            Pokemon(
                id = 1,
                name = "Pikachu",
                types = listOf(
                    Type(2, "fire"),
                    Type(3, "flying"),
                ),
                height = 6.0,
                weight = 0.4,
                abilities = listOf(
                    Ability(23, "blaze"),
                    Ability(13, "solar-power"),
                ),
                eggGroups = listOf(
                    EggGroup(13, "monster"),
                    EggGroup(15, "dragon"),
                ),
                stat = Stat(6, 78, 100, 84, 78, 109, 82),
                genus = "Mouse Pokémon",
                description = "Pidgey has an extremely sharp sense of direction. It is capable of unerringly returning home to its nest, however far it may be removed from its familiar surroundings.",
            ),
        )
        val expectedPage = PageImpl(pokemonList, pageable, pokemonList.size.toLong())
        `when`(pokemonRepository.findAll(pageable)).thenReturn(expectedPage)

        val resultPage = pokemonService.getAllPokemon(pageable)

        assertEquals(expectedPage, resultPage)
    }

    @Test
    fun testGetPokemonByID_ExistingId_ReturnsPokemon() {
        val id = 1
        val expectedPokemon = Pokemon(
            1, "Pikachu",
            listOf(Type(2, "fire"), Type(3, "flying")),
            6.0, 0.4,
            listOf(Ability(23, "blaze"), Ability(13, "solar-power")),
            listOf(EggGroup(13, "monster"), EggGroup(15, "dragon")),
            Stat(6, 78, 100, 84, 78, 109, 82),
            "Mouse Pokémon",
            "Pikachu has an extremely sharp sense of direction. It is capable of unerringly returning home to its nest, however far it may be removed from its familiar surroundings.",
        )

        `when`(pokemonRepository.existsById(id)).thenReturn(true)
    }

    @Test
    fun testGetPokemonByID_NonExistingId_ThrowsNoSuchElementException() {
        val id = 1
        `when`(pokemonRepository.findAllById(mutableListOf(id))).thenReturn(mutableListOf())

        assertThrows(PokemonNotFoundException::class.java) {
            pokemonService.getPokemonByID(id)
        }
    }

    @Test
    fun filterPokemonByType_ShouldReturnFilteredPokemonByType() {
        val typeName = "fire"
        val pokemonList = listOf(
            Pokemon(
                id = 1,
                name = "Charizard",
                types = listOf(
                    Type(2, "fire"),
                    Type(3, "flying"),
                ),
                height = 6.1,
                weight = 90.5,
                abilities = listOf(
                    Ability(23, "blaze"),
                    Ability(13, "solar-power"),
                ),
                eggGroups = listOf(
                    EggGroup(13, "monster"),
                    EggGroup(15, "dragon"),
                ),
                stat = Stat(6, 78, 100, 84, 78, 109, 82),
                genus = "Flame Pokémon",
                description = "Charizard flies around the sky in search of powerful opponents.",
            ),
            Pokemon(
                id = 2,
                name = "Arcanine",
                types = listOf(
                    Type(2, "fire"),
                ),
                height = 1.9,
                weight = 155.0,
                abilities = listOf(
                    Ability(23, "intimidate"),
                    Ability(13, "flash-fire"),
                ),
                eggGroups = listOf(
                    EggGroup(13, "field"),
                ),
                stat = Stat(6, 90, 110, 80, 80, 95, 82),
                genus = "Legendary Pokémon",
                description = "Arcanine is known for its high speed.",
            ),
            Pokemon(
                id = 3,
                name = "Bulbasaur",
                types = listOf(
                    Type(1, "grass"),
                    Type(2, "poison"),
                ),
                height = 0.7,
                weight = 6.9,
                abilities = listOf(
                    Ability(23, "overgrow"),
                    Ability(13, "chlorophyll"),
                ),
                eggGroups = listOf(
                    EggGroup(13, "monster"),
                    EggGroup(15, "plant"),
                ),
                stat = Stat(6, 45, 49, 49, 65, 65, 45),
                genus = "Seed Pokémon",
                description = "Bulbasaur can be seen napping in bright sunlight.",
            ),
        )

        `when`(pokemonRepository.findPokemonByTypesType(typeName)).thenReturn(pokemonList)
        val resultPage = pokemonService.filterPokemonByType(typeName)

        assertEquals(pokemonList, resultPage)
    }
}
