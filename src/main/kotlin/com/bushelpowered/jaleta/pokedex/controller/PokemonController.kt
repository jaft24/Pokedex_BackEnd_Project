package com.bushelpowered.jaleta.pokedex.controller


import com.bushelpowered.jaleta.pokedex.model.Pokemon
import com.bushelpowered.jaleta.pokedex.model.Types
import com.bushelpowered.jaleta.pokedex.service.CapturedService
import com.bushelpowered.jaleta.pokedex.service.PokemonService
import com.nimbusds.jose.shaded.gson.Gson
import com.nimbusds.jose.shaded.gson.reflect.TypeToken
import com.nimbusds.jwt.SignedJWT
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pokemon")
class PokemonController(private var pokemonService: PokemonService) {

    lateinit var capturedService: CapturedService
       @GetMapping("/test")
    //    fun test():String = pokemonService.test()
    fun test():String {

           val authorizationHeader: String = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJFaHFpU3VCUGdmT1lUdkJrOVlWWHlyYk1vd1NNZkotbDBUVlQtdWFLS3dBIn0.eyJleHAiOjE2ODU2NDE0MTcsImlhdCI6MTY4NTY0MTExNywianRpIjoiMzNmYTU2NmMtYzczMy00YWRkLTg4NmUtZDE3ZDAwM2VjNTM4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9wb2tlZGV4YXBpIiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJhY2NvdW50Il0sInN1YiI6IjBmMGZhMjgzLTdmZmItNGU1Ny1hMzhlLTMyY2FjOGNhYjk1NiIsInR5cCI6IkJlYXJlciIsImF6cCI6InRyYWluZXIiLCJzZXNzaW9uX3N0YXRlIjoiOThjOGJhZTAtZTk0Ni00OTE5LThmMWItNGVlYTBlNTgzMTIyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODEvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXBva2VkZXhhcGkiLCJ1bWFfYXV0aG9yaXphdGlvbiIsInVzZXIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJyZWFsbS1tYW5hZ2VtZW50Ijp7InJvbGVzIjpbIm1hbmFnZS1yZWFsbSIsIm1hbmFnZS11c2VycyIsInZpZXctdXNlcnMiLCJxdWVyeS1ncm91cHMiLCJxdWVyeS11c2VycyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJ0cmFpbmVyIHByb2ZpbGUgZW1haWwiLCJzaWQiOiI5OGM4YmFlMC1lOTQ2LTQ5MTktOGYxYi00ZWVhMGU1ODMxMjIiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IkFsZXggU21pdGgiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhc21pdGgiLCJnaXZlbl9uYW1lIjoiQWxleCIsImZhbWlseV9uYW1lIjoiU21pdGgiLCJlbWFpbCI6ImFsZXhAZ21haWwuY29tIn0.dG31MvYsEIQJfYlUmZ6VbbfGakmO18l1z5tkcnwGZdb0qZrZ9W3FJT5-otF5mIZWE5gMF6OiU4Bc78186SIUB9AvFaCpyUqKh-ncgBc3qtWSJ2-dVNF5ovhZdu2TW_n1aqlsXub8IZTMQDLEwpyS4HNLrTUxbAfSkfblKfoaf2yResWrxOpejcV_huRtRco11sumypM6-ah9dtr3c0D2-OTW62W-_-rwpCPpTbk68KW_xvVlCfV4E1XybskbB_p47ap7B-FlF9wBUn0pethPYXzo9AX252gOy7LBlq6IAie25nKZTVbO6LF3fbgb_xXVTJK99Swd2oBStH93l58mOw"
           val decodedPayload = SignedJWT.parse(authorizationHeader).payload.toString()
           val gson = Gson()
           val mapType = object : TypeToken<Map<String, Any>>() {}.type
           val map: Map<String, Any> = gson.fromJson(decodedPayload, mapType)
           val id = map["sub"] as String
           val userName = map["preferred_username"]

           return ("$userName has a keycloak id $id")
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun notFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    // All Pokemon
    @GetMapping("/all{page}{size}")
    fun getAllPokemon(@PathVariable @RequestParam(defaultValue = "1") page: Int,
                      @PathVariable @RequestParam(defaultValue = "10") size: Int): Page<Pokemon> = pokemonService.getAllPokemon(page, size)

    @GetMapping("/byId/{id}")
    fun getPokemonById(@PathVariable id: Int):List<Pokemon> = pokemonService.getPokemonByID(id)

    @GetMapping("/byName/{name}")
    fun getPokemonByName(@PathVariable name: String):List<Pokemon> = pokemonService.getPokemonByName(name)

//    @GetMapping("/byHeight/{height}{page}{size}")
//    fun getPokemonByHeight(@PathVariable height: Float,
//                           @PathVariable @RequestParam(defaultValue = "1") page: Int,
//                           @PathVariable @RequestParam(defaultValue = "10") size: Int):Page<Pokemon> = pokemonService.getPokemonByHeight(height,page,size)

//    @GetMapping("/byWeight/{weight}")
//    fun getPokemonByWeight(@PathVariable weight: Float):Page<Pokemon> = pokemonService.getPokemonByWeight(weight)

    @GetMapping("/byHeight")
    fun findByHeight(
        @RequestParam height: Float,
        pageable: Pageable
    ): ResponseEntity<Page<Pokemon>> {
        val pokemons = pokemonService.findByHeight(height, pageable)
        return ResponseEntity.ok(pokemons)
    }

    @GetMapping("/types/{typeId}/pokemon")
    fun getAllTypesByTagId(@PathVariable(value = "typeId") typeId: Int): List<Pokemon> = pokemonService.findPokemonByTypesId(typeId)

//        if (!tagRepository.existsById(tagId)) {
//            throw ResourceNotFoundException("Not found Tag with id = $tagId")
//        }
//
//        val tutorials: List<Tutorial> = tutorialRepository.findTutorialsByTagsId(tagId)
//        return ResponseEntity(tutorials, HttpStatus.OK)
//    }

//    @GetMapping("/byType")
//    fun findByTypes(
//        @RequestParam type: String
//    ): ResponseEntity<Types> {
//        val pokemons = pokemonService.findByTypes(type)
//        return ResponseEntity.ok(pokemons)
//    }






}



