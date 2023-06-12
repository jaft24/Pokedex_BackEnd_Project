package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.exception.AlreadyCapturedException
import com.bushelpowered.jaleta.pokedex.exception.MoreThanFiveCapturedException
import com.bushelpowered.jaleta.pokedex.exception.NoPokemonCapturedException
import com.bushelpowered.jaleta.pokedex.exception.PokemonNotCapturedException
import com.bushelpowered.jaleta.pokedex.exception.PokemonNotFoundException
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

        if (!pokemonRepository.existsById(pokemonId)) {
            throw PokemonNotFoundException()
        } else {
            if (capturedRepository.existsByTrainerIdAndPokemonId(capture.trainerId, capture.pokemonId)) {
                throw AlreadyCapturedException()
            } else if (capturedRepository.countAllByTrainerId(capture.trainerId) >= maxCaptureAmount) {
                throw MoreThanFiveCapturedException()
            }
        }
        capturedRepository.save(capture)
    }

    fun getAllCapturedPokemonByTrainerId(autHeader: Authentication): List<Pokemon> {
        val capturedList = capturedRepository.findByTrainerId(autHeader.name)
        val pokemonIdList = mutableListOf<Int>()

        if (!capturedRepository.existsByTrainerId(autHeader.name)) {
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
        } else if (!capturedRepository.existsByTrainerIdAndPokemonId(autHeader.name, pokemonId)) {
            throw PokemonNotCapturedException()
        }
        capturedRepository.deleteByTrainerIdAndPokemonId(autHeader.name, pokemonId)
    }

    fun deleteAllByTrainerId(autHeader: Authentication) {
        if (!capturedRepository.existsByTrainerId(autHeader.name)) {
            throw NoPokemonCapturedException()
        }
        capturedRepository.deleteAllByTrainerId(autHeader.name)
    }
}
