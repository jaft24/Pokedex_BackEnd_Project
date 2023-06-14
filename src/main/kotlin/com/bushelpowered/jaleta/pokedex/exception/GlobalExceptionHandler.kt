package com.bushelpowered.jaleta.pokedex.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(PokemonNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handlePokemonNotFoundException(ex: PokemonNotFoundException): String {
        return ex.message ?: "Pokemon with this description not found, please try again."
    }

    @ExceptionHandler(AlreadyCapturedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleAlreadyCapturedException(ex: AlreadyCapturedException): String {
        return "You have already captured this pokemon, please try to capture another pokemon."
    }

    @ExceptionHandler(MaxCapturedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handleMoreThanFiveCapturedException(ex: MaxCapturedException): String {
        return "You have captured the maximum allowed pokemon, please release some of your pokemon and try again"
    }

    @ExceptionHandler(NoPokemonCapturedException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNoPokemonCapturedException(ex: NoPokemonCapturedException): String {
        return "You have not captured any pokemon, please capture a few pokemon."
    }

    @ExceptionHandler(PokemonNotCapturedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun handlePokemonNotCapturedException(ex: PokemonNotCapturedException): String {
        return "You have not captured this pokemon yet!"
    }
}
