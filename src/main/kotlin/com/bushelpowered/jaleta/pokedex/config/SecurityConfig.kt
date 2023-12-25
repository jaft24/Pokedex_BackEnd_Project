package com.bushelpowered.jaleta.pokedex.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and()
                .authorizeRequests { requests ->
                    requests
                            .antMatchers("/api/capture/**").hasAuthority("SCOPE_trainer")
                            .antMatchers(HttpMethod.DELETE, "/api/capture/**").hasAuthority("SCOPE_trainer")
                            .antMatchers("/api/pokemon/**").permitAll()
                            .anyRequest().permitAll()
                }

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
