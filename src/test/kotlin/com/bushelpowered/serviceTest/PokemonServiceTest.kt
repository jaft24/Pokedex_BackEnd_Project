package com.bushelpowered.serviceTest

import com.bushelpowered.entity.Ability
import com.bushelpowered.entity.EggGroup
import com.bushelpowered.entity.Pokemon
import com.bushelpowered.entity.Stat
import com.bushelpowered.entity.Type
import com.bushelpowered.exception.PokemonNotFoundException
import com.bushelpowered.repository.PokemonRepository
import com.bushelpowered.service.PokemonService
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
            listOf(
                Type(2, "fire"),
                Type(3, "flying"),
            ),
            6.0, 0.4,
            listOf(
                Ability(23, "blaze"),
                Ability(13, "solar-power"),
            ),
            listOf(
                EggGroup(13, "monster"),
                EggGroup(15, "dragon"),
            ),
            Stat(6, 78, 100, 84, 78, 109, 82),
            "Mouse Pokémon",
            "Pikachu has an extremely sharp sense of direction. It is capable of unerringly returning home to its nest, however far it may be removed from its familiar surroundings.",
        )

        `when`(pokemonRepository.existsById(id)).thenReturn(true)
        `when`(pokemonRepository.findAllById(id)).thenReturn(expectedPokemon)

        val result = pokemonService.getPokemonByID(id)

        assertEquals(expectedPokemon, result)
    }

    @Test
    fun testGetPokemonByID_NonExistingId_ThrowsNoSuchElementException() {
        val id = 1
        `when`(pokemonRepository.findAllById(mutableListOf(id))).thenReturn(mutableListOf())

        assertThrows(PokemonNotFoundException::class.java) {
            pokemonService.getPokemonByID(id)
        }
    }

}
