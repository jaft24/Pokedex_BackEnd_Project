package com.bushelpowered.controller

import com.bushelpowered.entity.Pokemon
import com.bushelpowered.service.CaptureService
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
    fun catchPokemonById(auth: Authentication, @PathVariable id: Int) {
        captureService.catchPokemonById(auth, id)
    }

    @GetMapping("/getAll")
    fun getAllCapturedPokemonByTrainerId(auth: Authentication): ResponseEntity<List<Pokemon>> {
        val capturedPokemonList = captureService.getAllCapturedPokemonByTrainerId(auth)
        return ResponseEntity.ok(capturedPokemonList)
    }

    @DeleteMapping("/remove/{id}")
    fun deleteByTrainerIdAndPokemonId(auth: Authentication, @PathVariable id: Int) {
        captureService.deleteByTrainerIdAndPokemonId(auth, id)
    }

    @DeleteMapping("/removeAll")
    fun deleteAllByTrainerId(auth: Authentication) {
        captureService.deleteAllByTrainerId(auth)
    }
}
