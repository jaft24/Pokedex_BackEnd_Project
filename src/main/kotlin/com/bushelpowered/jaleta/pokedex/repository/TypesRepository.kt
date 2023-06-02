package com.bushelpowered.jaleta.pokedex.repository

import com.bushelpowered.jaleta.pokedex.model.Types
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypesRepository: JpaRepository<Types, Int> {

    fun findTypesByPokemonId(pokemonId: Int): List<Types>


}