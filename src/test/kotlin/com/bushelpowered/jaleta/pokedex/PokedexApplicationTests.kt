package com.bushelpowered.jaleta.pokedex

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


@SpringBootTest
class PokedexApplicationTests {

	@Bean
	fun restTemplate(): RestTemplate? {
		return RestTemplate()
	}

}
