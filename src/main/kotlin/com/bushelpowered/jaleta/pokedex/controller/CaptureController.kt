package com.bushelpowered.jaleta.pokedex.controller

import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.service.CaptureService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Transactional
@RestController
@RequestMapping("/api/capture")
class CaptureController(private val captureService: CaptureService) {

    @PostMapping("/{id}")
    fun catchPokemonById(aut: Authentication, @PathVariable id: Int) {
        captureService.catchPokemonById(aut, id)
    }

    @GetMapping("/getAll")
    fun getAllCapturedPokemonByTrainerId(autHeader: Authentication): ResponseEntity<List<Pokemon>> {
        val capturedPokemonList = captureService.getAllCapturedPokemonByTrainerId(autHeader)
        return ResponseEntity.ok(capturedPokemonList)
    }

    @DeleteMapping("/remove/{id}")
    fun deleteByTrainerIdAndPokemonId(autHeader: Authentication, @PathVariable id: Int) {
        captureService.deleteByTrainerIdAndPokemonId(autHeader, id)
    }

    @DeleteMapping("/removeAll")
    fun deleteAllByTrainerId(autHeader: Authentication) {
        captureService.deleteAllByTrainerId(autHeader)
    }
}
