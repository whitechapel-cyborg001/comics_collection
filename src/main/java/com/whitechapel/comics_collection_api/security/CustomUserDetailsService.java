package com.whitechapel.comics_collection_api.security;

import com.whitechapel.comics_collection_api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Bean de UserDetailsService para Spring Security
 * 
 * Función:
 *  - Permite cargar usuarios desde la base de datos
 *  - Necesario para JwtAuthenticationFilter y autenticación JWT
 */
@Configuration
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
