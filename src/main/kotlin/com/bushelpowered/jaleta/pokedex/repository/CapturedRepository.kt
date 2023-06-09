package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Capture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CapturedRepository : JpaRepository<Capture, Int> {

    fun findByTrainerId(trainerId: String): List<Capture>
    fun deleteAllByTrainerId(trainerId: String)
    fun deleteByTrainerIdAndPokemonId(trainerId: String, pokemonId: Int)
    fun existsByTrainerIdAndPokemonId(trainerId: String, pokemonId: Int): Boolean
    fun countAllByTrainerId(trainerId: String): Int
}
