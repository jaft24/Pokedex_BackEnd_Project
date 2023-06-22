package com.bushelpowered.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/pokemon/**").permitAll()
            .requestMatchers("/api/capture/**").hasAuthority("SCOPE_trainer")
            .anyRequest().permitAll()

        http.cors().and()
            .logout()
            .logoutUrl("/api/capture/logout")
            .logoutSuccessUrl("/api/pokemon/all")
            .clearAuthentication(true)
            .invalidateHttpSession(true)

        http.oauth2ResourceServer()
            .jwt()

        return http.build()
    }
}
