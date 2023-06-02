package com.bushelpowered.jaleta.pokedex.service
import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.repository.PokemonRepository
import com.bushelpowered.jaleta.pokedex.repository.TypesRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam

@Service
class PokemonService(private var pokemonRepository: PokemonRepository, private var typesRepository: TypesRepository) {

    //fun test():String = "Hello World From Pokemon Service Layer"
    // Task: Paginate all these lists.

    // Get all Pokemon
    fun getAllPokemon(@RequestParam(defaultValue = "1") page: Int, @RequestParam(defaultValue = "10") size: Int): Page<Pokemon> {
        // In case the pokemon data base is deleted (lol)
        if (pokemonRepository.findAll().isEmpty()){
            throw NoSuchElementException("There are no pokemons in this schema")
        }
        return pokemonRepository.findAll(
                PageRequest.of(
                    page -1,
                    size )
            )

    }

    // Get Pokemon by Id
    fun getPokemonByID(id: Int): List<Pokemon>{
//        if (pokemonRepository.findAllById(mutableListOf(id)).isEmpty()){
//            throw NoSuchElementException("There is no pokemon with this Id number")
//        }
        return pokemonRepository.findAllById(mutableListOf(id))
    }

    // Get Pokemon by Name
    fun getPokemonByName(name: String): List<Pokemon>{
        if (pokemonRepository.findByName(name).isEmpty()){
            throw NoSuchElementException("There is no pokemon with this name")
        }
        return pokemonRepository.findByName(name) }

   // Get Pokemon by Height (Height Filter)
//    fun getPokemonByHeight(height: Float,
//                           @RequestParam(defaultValue = "1") page: Int,
//                           @RequestParam(defaultValue = "5") size: Int): Page<Pokemon>{
//       if (pokemonRepository.findByHeight(height).isEmpty()){
//           throw NoSuchElementException("There is no pokemon with this height")
//       }
//        return pokemonRepository.findTheHeight(height,PageRequest.of(0,5,Sort.by(Sort.Order.asc("id"))))
////        return pokemonRepository.findAll(
////            PageRequest.of(
////                page -1,
////                size,
////        )
//   }

    fun findByHeight(height: Float, pageable: Pageable): Page<Pokemon> {
        val fixedPageSize = 10
        val updatedPageable = PageRequest.of(pageable.pageNumber, fixedPageSize, pageable.sort)
        return pokemonRepository.findByHeight(height, updatedPageable)
    }

//    @GetMapping("/types/{typeId}/pokemon")
//    fun getAllTutorialsByTagId(@PathVariable(value = "tagId") tagId: Long): ResponseEntity<List<Tutorial>> {
//        if (!tagRepository.existsById(tagId)) {
//            throw ResourceNotFoundException("Not found Tag with id = $tagId")
//        }
//
//        val tutorials: List<Tutorial> = tutorialRepository.findTutorialsByTagsId(tagId)
//        return ResponseEntity(tutorials, HttpStatus.OK)
//    }

    fun findPokemonByTypesId(typeId: Int): List<Pokemon> {
        if (!typesRepository.existsById(typeId)) {
            throw NoSuchElementException("Not found Tag with id = $typeId")
        }
        return pokemonRepository.findPokemonByTypesId(typeId)
    }


//    fun findByTypes(type: String): Types {
//        val typeEntity = typesRepository.findByType(type)
// //       return if (typeEntity != null) {
//        return  pokemonRepository.findByTypesType(typeEntity)
////        } else {
////            emptyList()
////        }



    // Get Pokemon by Weight (Weight Filter)
//    fun getPokemonByWeight(weight: Float): Page<Pokemon>{
//        if (pokemonRepository.findByWeight(weight).isEmpty()){
//            throw NoSuchElementException("There is no pokemon with this weight")
//        }
//        return pokemonRepository.findByWeight(weight) }










}
