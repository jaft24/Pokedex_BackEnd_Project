package com.bushelpowered.jaleta.pokedex.controller

import com.bushelpowered.jaleta.pokedex.model.Captured
import com.bushelpowered.jaleta.pokedex.service.CapturedService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder

import org.springframework.web.context.request.ServletRequestAttributes




@RestController
@RequestMapping("/api/capture")
class CapturedController(private val capturedService: CapturedService) {

    @GetMapping("/1")
    fun testAuthentication(): String {
        return "Pokemon 1 is captured"
    }

    @GetMapping("/{id}")
    fun getCapturedByID( @PathVariable id: Int, aut: Authentication): List<Captured> {
        println (aut.name)
        return capturedService.getCapturedByID(id)
    }



    @PostMapping("/pokemon/{id}")
 //   @PreAuthorize("hasRole('trainer')")
    fun catchPokemonById(@PathVariable id: Int, aut: Authentication) {
        println (aut.name)
        capturedService.catchPokemonById("testTrainerId-001-223",id)
    }




    // fun capturePokemon(@RequestBody captured: Captured, @RequestHeader("Authorization") ) = capturedService.catchPokemonById(captured)


    // Task: create a login so they could browse and capture or uncapture

//    @PostMapping("/byID/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
    // fun capturePokemon(@RequestBody captured: Captured, @RequestHeader("Authorization") ) = capturedService.catchPokemonById(captured)
//    private val AUTHORIZATION_HEADER: String? = "Authorization"
//
//    fun getBearerTokenHeader(): String? {
//        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request.getHeader("Authorization")
//    }
//
//    fun apply(requestTemplate: RequestTemplate) {
//        requestTemplate.header(AUTHORIZATION_HEADER, getBearerTokenHeader())
//    }

//    @DeleteMapping("/uncapture")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun uncapturePokemon(@RequestBody captured: Captured) = capturedService.leavePokemonById(captured)


//    @GetMapping("/example")
//    fun exampleEndpoint(@RequestHeader(HttpHeaders.AUTHORIZATION) authorizationHeader: String?): String {
//        val token = authorizationHeader?.removePrefix("Bearer ") // Remove "Bearer " prefix if present
//
//        if (token != null) {
//            try {
//                val jwtParser = Jwts.parserBuilder().setSigningKey("yourSecretKey").build()
//                val claims = jwtParser.parseClaimsJws(token).body
//                val id = claims["id"] as String
//
//                // Save the ID in a database or perform any other required operations
//
//                return "ID $id extracted from JWT and saved successfully"
//            } catch (ex: Exception) {
//                return "Error decoding JWT: ${ex.message}"
//            }
//        }
//
//        return "Token not found"
//    }




}

