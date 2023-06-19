package com.bushelpowered.controllerTest

import com.bushelpowered.controller.CaptureController
import com.bushelpowered.entity.Ability
import com.bushelpowered.entity.EggGroup
import com.bushelpowered.entity.Pokemon
import com.bushelpowered.entity.Stat
import com.bushelpowered.entity.Type
import com.bushelpowered.service.CaptureService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

internal class CaptureControllerTest {
    private var captureController: CaptureController? = null

    @Mock
    private val captureService: CaptureService? = null

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        captureController = CaptureController(captureService!!)
    }

    @Test
    fun testCatchPokemonById() {
        val pokemonId = 1
        val authentication = Mockito.mock(
            Authentication::class.java,
        )
        captureController!!.catchPokemonById(authentication, pokemonId)

        Mockito.verify(captureService, Mockito.times(1))?.catchPokemonById(authentication, pokemonId)
    }

    @Test
    fun testGetAllCapturedPokemonByTrainerId() {
        val capturedPokemonList = listOf(
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
        )
        val expectedResponse = ResponseEntity.ok(capturedPokemonList)
        val authentication = Mockito.mock(
            Authentication::class.java,
        )
        Mockito.`when`(captureService!!.getAllCapturedPokemonByTrainerId(authentication))
            .thenReturn(capturedPokemonList)

        val response = captureController!!.getAllCapturedPokemonByTrainerId(authentication)
        Assertions.assertEquals(expectedResponse, response)

        Mockito.verify(captureService, Mockito.times(1)).getAllCapturedPokemonByTrainerId(authentication)
    }
}
