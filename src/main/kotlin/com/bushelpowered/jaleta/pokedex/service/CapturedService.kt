package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.model.Capture
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.repository.CapturedRepository
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CapturedService(private val capturedRepository: CapturedRepository, private val pokemonRepository: PokemonRepository) {

    private final val maxCaptureAmount = 5

    fun catchPokemonById(autHeader: Authentication, pokemonId: Int) {
        val capture = Capture()
        capture.trainerId = autHeader.name
        capture.pokemonId = pokemonId

        if (capturedRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)) {
            throw IllegalArgumentException("You have already captured this Pokemon.")
        } else if(capturedRepository.countAllByTrainerId(capture.trainerId) >= maxCaptureAmount) {
            throw IllegalArgumentException("You can't capture more than five Pokemon.")
        } else {
            capturedRepository.save(capture)
        }
    }

    fun getAllCapturedPokemonByTrainerId(autHeader: Authentication): List<Pokemon> {
        val capturedList = capturedRepository.findByTrainerId(autHeader.name)
        val pokemonIdList = mutableListOf<Int>()

        capturedList.forEach { captured ->
            pokemonIdList.add(captured.pokemonId)
        }
        return pokemonRepository.findAllById(pokemonIdList)
    }

    fun deleteByTrainerIdAndPokemonId (autHeader: Authentication, pokemonId: Int) {
        capturedRepository.deleteByTrainerIdAndPokemonId(autHeader.name, pokemonId)
    }

    fun deleteAllByTrainerId (autHeader: Authentication) {
        capturedRepository.deleteAllByTrainerId(autHeader.name)
    }

}