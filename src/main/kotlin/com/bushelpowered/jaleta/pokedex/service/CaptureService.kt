package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.exception.AlreadyCapturedException
import com.bushelpowered.jaleta.pokedex.exception.MaxCapturedException
import com.bushelpowered.jaleta.pokedex.exception.NoPokemonCapturedException
import com.bushelpowered.jaleta.pokedex.exception.PokemonNotCapturedException
import com.bushelpowered.jaleta.pokedex.exception.PokemonNotFoundException
import com.bushelpowered.jaleta.pokedex.model.Capture
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.repository.CaptureRepository
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CaptureService(private val captureRepository: CaptureRepository, private val pokemonRepository: PokemonRepository) {

    private final val maxCaptureAmount = 5

    fun catchPokemonById(autHeader: Authentication, pokemonId: Int) {
        val capture = Capture()
        capture.trainerId = autHeader.name
        capture.pokemonId = pokemonId

        if (!pokemonRepository.existsById(pokemonId)) {
            throw PokemonNotFoundException()
        } else {
            if (captureRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)) {
                throw AlreadyCapturedException()
            } else if (captureRepository.countAllByTrainerId(capture.trainerId) >= maxCaptureAmount) {
                throw MaxCapturedException()
            }
        }
        captureRepository.save(capture)
    }

    fun getAllCapturedPokemonByTrainerId(autHeader: Authentication): List<Pokemon> {
        val capturedList = captureRepository.findByTrainerId(autHeader.name)
        val pokemonIdList = mutableListOf<Int>()

        if (!captureRepository.existsByTrainerId(autHeader.name)) {
            throw NoPokemonCapturedException()
        }
        capturedList.forEach { captured ->
            pokemonIdList.add(captured.pokemonId)
        }
        return pokemonRepository.findAllById(pokemonIdList)
    }

    fun deleteByTrainerIdAndPokemonId(autHeader: Authentication, pokemonId: Int) {
        if (!pokemonRepository.existsById(pokemonId)) {
            throw PokemonNotFoundException()
        } else if (!captureRepository.existsByTrainerIdAndPokemonId(autHeader.name, pokemonId)) {
            throw PokemonNotCapturedException()
        }
        captureRepository.deleteByTrainerIdAndPokemonId(autHeader.name, pokemonId)
    }

    fun deleteAllByTrainerId(autHeader: Authentication) {
        if (!captureRepository.existsByTrainerId(autHeader.name)) {
            throw NoPokemonCapturedException()
        }
        captureRepository.deleteAllByTrainerId(autHeader.name)
    }
}
