package com.bushelpowered.jaleta.pokedex.service

import com.bushelpowered.jaleta.pokedex.model.Captured
import com.bushelpowered.jaleta.pokedex.repository.CapturedRepository
import com.nimbusds.jwt.SignedJWT
import org.springframework.expression.ParseException
import org.springframework.stereotype.Service


@Service
class CapturedService(private val capturedRepository: CapturedRepository) {

    private lateinit var captured: Captured

    fun getCapturedByID (id: Int): List<Captured> {
        return capturedRepository.findAllById(mutableListOf(id))
    }

//    fun catchPokemonById(captured: Captured): Captured {
//
////        val elements = authorizationHeader.split('.')
////        if (elements.size == 3) {
////            val (header, payload, signature) = elements
////
////            captured.trainerId = authorizationHeader
////            captured.pokemonId = pokemonId
////            capturedRepository.save(captured)
////            return captured
////        }
////        val decodedPayload = SignedJWT.parse(authorizationHeader).payload.toString()
////        captured.trainerId = SignedJWT.parse(authorizationHeader).payload.toString()
////        captured.pokemonId = pokemonId
////        capturedRepository.save(captured)
////        return captured
//
////        capturedRepository.save(captured)
////        return captured
//
//
//    }




    fun catchPokemonById(authorizationHeader: String, pokemonId: Int): Captured {
            captured.trainerId = authorizationHeader
            captured.pokemonId = pokemonId
            capturedRepository.save(captured)
            return captured
        }












////        val decodedPayload = SignedJWT.parse(authorizationHeader).payload.toString()
////        captured.trainerId = SignedJWT.parse(authorizationHeader).payload.toString()
////        captured.pokemonId = pokemonId
////        capturedRepository.save(captured)
////        return captured
//
//            captured.trainerId = authorizationHeader
//            captured.pokemonId = pokemonId
//            capturedRepository.save(captured)
//            return captured
//
//
//    }
        // Leave a captured Pokemon
        fun leavePokemonById(captured: Captured) {
            // capturedRepository.deleteById(captured.id)
        }



}