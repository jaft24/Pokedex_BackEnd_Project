package com.bushelpowered.jaleta.pokedex

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class PokedexApplication

fun main(args: Array<String>) {
	runApplication<PokedexApplication>(*args)

//	@Bean
//	fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
//		return builder.build()
//	}

}
