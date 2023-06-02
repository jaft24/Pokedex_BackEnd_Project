package com.bushelpowered.jaleta.pokedex.config

import com.nimbusds.jwt.SignedJWT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.expression.ParseException
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy



@Configuration
@EnableWebSecurity
class SecurityConfig {

//    @Bean
//    protected fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy? {
//        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
//    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().cors().and()

            .authorizeHttpRequests()
               // .requestMatchers(HttpMethod.POST).hasAuthority("write")
                .requestMatchers("/api/capture/**").hasAuthority("SCOPE_trainer")
                .requestMatchers("/api/pokemon/**").permitAll()
                //.requestMatchers(HttpMethod.POST, "/api/capture/byId/**").hasAuthority("SCOPE_trainer")
                //.anyRequest().denyAll()

               http.oauth2ResourceServer()
               .jwt()
      //  http.oauth2ResourceServer { obj: OAuth2ResourceServerConfigurer<HttpSecurity?> -> obj.jwt() }

        return http.build()
    }

//        http.cors().and()
//            .logout()
//            .logoutUrl("/api/capture/logout") // Specify the logout URL
//            .logoutSuccessUrl("/api/pokemon/all") // Specify the URL to redirect after successful logout
//            .invalidateHttpSession(true) // Invalidate the HttpSession



}

