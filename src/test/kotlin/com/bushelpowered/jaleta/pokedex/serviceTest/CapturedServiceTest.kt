package com.bushelpowered.jaleta.pokedex.serviceTest

import com.bushelpowered.jaleta.pokedex.exception.AlreadyCapturedException
import com.bushelpowered.jaleta.pokedex.exception.MoreThanFiveCapturedException
import com.bushelpowered.jaleta.pokedex.model.Ability
import com.bushelpowered.jaleta.pokedex.model.Capture
import com.bushelpowered.jaleta.pokedex.model.EggGroup
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.model.Stat
import com.bushelpowered.jaleta.pokedex.model.Type
import com.bushelpowered.jaleta.pokedex.repository.CapturedRepository
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import com.bushelpowered.jaleta.pokedex.service.CapturedService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication

class CapturedServiceTest {

    @Mock
    private lateinit var capturedRepository: CapturedRepository

    @Mock
    private lateinit var pokemonRepository: PokemonRepository

    @InjectMocks
    private lateinit var capturedService: CapturedService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCatchPokemonById() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val pokemonId = 1
        val capture = Capture()
        capture.trainerId = authentication.name
        capture.pokemonId = pokemonId
        `when`(pokemonRepository.existsById(pokemonId)).thenReturn(true)
        `when`(capturedRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)).thenReturn(false)
        `when`(capturedRepository.countAllByTrainerId(capture.trainerId)).thenReturn(4)

        capturedService.catchPokemonById(authentication, pokemonId)

        verify(capturedRepository).save(capture)
    }

    @Test
    fun testCatchPokemonById_alreadyCaptured() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val pokemonId = 1
        val capture = Capture()
        capture.trainerId = authentication.name
        capture.pokemonId = pokemonId
        `when`(pokemonRepository.existsById(pokemonId)).thenReturn(true)
        `when`(capturedRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)).thenReturn(true)

        assertThrows(AlreadyCapturedException::class.java) {
            capturedService.catchPokemonById(authentication, pokemonId)
        }

        verify(capturedRepository, never()).save(capture)
    }

    @Test
    fun testCatchPokemonById_maxCaptureAmountReached() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val pokemonId = 1
        val capture = Capture()
        capture.trainerId = authentication.name
        capture.pokemonId = pokemonId
        `when`(pokemonRepository.existsById(pokemonId)).thenReturn(true)
        `when`(capturedRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)).thenReturn(false)
        `when`(capturedRepository.countAllByTrainerId(capture.trainerId)).thenReturn(5)

        assertThrows(MoreThanFiveCapturedException::class.java) {
            capturedService.catchPokemonById(authentication, pokemonId)
        }

        verify(capturedRepository, never()).save(capture)
    }

    @Test
    fun testGetAllCapturedPokemonByTrainerId() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val trainerId = authentication.name
        val capturedList = listOf(
            Capture(trainerId = trainerId, pokemonId = 1),
            Capture(trainerId = trainerId, pokemonId = 2),
            Capture(trainerId = trainerId, pokemonId = 3),
        )
        val pokemonIdList = listOf(1, 2, 3)
        val pokemonList = listOf(
            Pokemon(
                id = 1,
                name = "Pikachu",
                types = listOf(Type(2, "fire"), Type(3, "flying")),
                weight = 6.0,
                height = 0.4,
                abilities = listOf(Ability(23, "blaze"), Ability(13, "solar-power")),
                eggGroups = listOf(EggGroup(13, "monster"), EggGroup(15, "dragon")),
                stat = Stat(6, 78, 100, 84, 78, 109, 82),
                genus = "Mouse Pokémon",
                description = "Pidgey has an extremely sharp sense of direction. It is capable of unerringly returning home to its nest, however far it may be removed from its familiar surroundings.",
            ),
        )
        `when`(capturedRepository.existsByTrainerId(trainerId)).thenReturn(true)
        `when`(capturedRepository.findByTrainerId(trainerId)).thenReturn(capturedList)
        `when`(pokemonRepository.findAllById(pokemonIdList)).thenReturn(pokemonList)

        val result = capturedService.getAllCapturedPokemonByTrainerId(authentication)

        assertEquals(pokemonList, result)
    }

    @Test
    fun testDeleteByTrainerIdAndPokemonId() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val trainerId = authentication.name
        val pokemonId = 1
        `when`(pokemonRepository.existsById(pokemonId)).thenReturn(true)
        `when`(capturedRepository.existsByTrainerIdAndPokemonId(trainerId, pokemonId)).thenReturn(true)
        capturedService.deleteByTrainerIdAndPokemonId(authentication, pokemonId)

        verify(capturedRepository).deleteByTrainerIdAndPokemonId(trainerId, pokemonId)
    }

    @Test
    fun testDeleteAllByTrainerId() {
        val username = ""
        val password = ""
        val authentication: Authentication = UsernamePasswordAuthenticationToken(username, password)
        val trainerId = authentication.name
        `when`(capturedRepository.existsByTrainerId(trainerId)).thenReturn(true)

        capturedService.deleteAllByTrainerId(authentication)

        verify(capturedRepository).deleteAllByTrainerId(trainerId)
    }
}
