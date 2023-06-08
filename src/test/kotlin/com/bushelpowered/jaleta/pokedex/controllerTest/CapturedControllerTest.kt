package com.bushelpowered.jaleta.pokedex.controllerTest

import com.bushelpowered.jaleta.pokedex.model.*
import com.bushelpowered.jaleta.pokedex.controller.CapturedController
import com.bushelpowered.jaleta.pokedex.service.CapturedService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

internal class CapturedControllerTest {
    private var capturedController: CapturedController? = null

    @Mock
    private val capturedService: CapturedService? = null
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        capturedController = CapturedController(capturedService!!)
    }

    @Test
    fun testCatchPokemonById() {
        val pokemonId = 1
        val authentication = Mockito.mock(
            Authentication::class.java
        )
        capturedController!!.catchPokemonById(pokemonId, authentication)

        Mockito.verify(capturedService, Mockito.times(1))?.catchPokemonById(authentication, pokemonId)
    }

    @Test
    fun testGetAllCapturedPokemonByTrainerId() {
        val capturedPokemonList = listOf(
            Pokemon(
                id = 1,
                name = "Charizard",
                types = listOf(
                    Type(2, "fire"),
                    Type(3, "flying")
                ),
                height = 6.1,
                weight = 90.5,
                abilities = listOf(
                    Ability(23, "blaze"),
                    Ability(13, "solar-power")
                ),
                eggGroups = listOf(
                    EggGroup(13, "monster"),
                    EggGroup(15, "dragon")
                ),
                stats = Stats(6, 78, 100, 84, 78, 109, 82),
                genus = "Flame Pokémon",
                description = "Charizard flies around the sky in search of powerful opponents."
            ),
            Pokemon(
                id = 2,
                name = "Arcanine",
                types = listOf(
                    Type(2, "fire")
                ),
                height = 1.9,
                weight = 155.0,
                abilities = listOf(
                    Ability(23, "intimidate"),
                    Ability(13, "flash-fire")
                ),
                eggGroups = listOf(
                    EggGroup(13, "field")
                ),
                stats = Stats(6, 90, 110, 80, 80, 95, 82),
                genus = "Legendary Pokémon",
                description = "Arcanine is known for its high speed."
            )
        )
        val expectedResponse = ResponseEntity.ok(capturedPokemonList)
        val authentication = Mockito.mock(
            Authentication::class.java
        )
        Mockito.`when`(capturedService!!.getAllCapturedPokemonByTrainerId(authentication))
            .thenReturn(capturedPokemonList)

        val response = capturedController!!.getAllCapturedPokemonByTrainerId(authentication)
        Assertions.assertEquals(expectedResponse, response)

        Mockito.verify(capturedService, Mockito.times(1)).getAllCapturedPokemonByTrainerId(authentication)
    }

}