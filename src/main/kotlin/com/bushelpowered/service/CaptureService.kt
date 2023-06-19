package com.bushelpowered.service

import com.bushelpowered.exception.AlreadyCapturedException
import com.bushelpowered.exception.MaxCapturedException
import com.bushelpowered.exception.NoPokemonCapturedException
import com.bushelpowered.exception.PokemonNotCapturedException
import com.bushelpowered.exception.PokemonNotFoundException
import com.bushelpowered.entity.Capture
import com.bushelpowered.entity.Pokemon
import com.bushelpowered.repository.CaptureRepository
import com.bushelpowered.repository.PokemonRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CaptureService(private val captureRepository: CaptureRepository, private val pokemonRepository: PokemonRepository) {

    private final val maxCaptureAmount = 5

    fun catchPokemonById(auth: Authentication, pokemonId: Int) {
        val capture = Capture()
        capture.trainerId = auth.name
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

    fun getAllCapturedPokemonByTrainerId(auth: Authentication): List<Pokemon> {
        val capturedList = captureRepository.findByTrainerId(auth.name)
        val pokemonIdList = mutableListOf<Int>()

        if (!captureRepository.existsByTrainerId(auth.name)) {
            throw NoPokemonCapturedException()
        }
        capturedList.forEach { captured ->
            pokemonIdList.add(captured.pokemonId)
        }
        return pokemonRepository.findAllById(pokemonIdList)
    }

    fun deleteByTrainerIdAndPokemonId(auth: Authentication, pokemonId: Int) {
        if (!pokemonRepository.existsById(pokemonId)) {
            throw PokemonNotFoundException()
        } else if (!captureRepository.existsByTrainerIdAndPokemonId(auth.name, pokemonId)) {
            throw PokemonNotCapturedException()
        }
        captureRepository.deleteByTrainerIdAndPokemonId(auth.name, pokemonId)
    }

    fun deleteAllByTrainerId(auth: Authentication) {
        if (!captureRepository.existsByTrainerId(auth.name)) {
            throw NoPokemonCapturedException()
        }
        captureRepository.deleteAllByTrainerId(auth.name)
    }
}
