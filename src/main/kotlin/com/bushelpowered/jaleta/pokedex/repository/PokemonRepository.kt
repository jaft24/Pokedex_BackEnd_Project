package com.bushelpowered.jaleta.pokedex.repository


import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.model.Types
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonRepository: JpaRepository<Pokemon, Int> {
    fun findByName(name: String): List<Pokemon>
    // fun findByWeight(weight: Float): Page<Pokemon>
   //  fun findByHeight(height: Float): List<Pokemon>
   //  fun findTheHeight(height: Float, pageable: Pageable): Page<Pokemon>

    fun findByHeight(height: Float, pageable: Pageable): Page<Pokemon>

    fun findPokemonByTypesId(typeId: Int): List<Pokemon>




//  fun findByTypesType(types: Types): Types
//  {
//       return typeRepository.findByType(types.type)
//
//    }

}
