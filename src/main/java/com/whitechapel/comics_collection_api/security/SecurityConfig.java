package com.whitechapel.comics_collection_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de Spring Security para JWT.
 *
 * Funciones:
 *  - Define qué endpoints son públicos y cuáles requieren autenticación
 *  - Configura filtro JwtAuthenticationFilter
 *  - Configura autenticación sin sesiones (stateless)
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // REST API sin CSRF
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/",
                    "/api/auth/**").permitAll()
                .anyRequest().authenticated()
            );

        // Añade nuestro filtro JWT antes del filtro de autenticación de Spring
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

/* Flujo de seguridad JWT
┌──────────────────────────────┐
│        Cliente (Postman /    │
│        Frontend)             │
└─────────────┬────────────────┘
              │
              │ POST /auth/login
              │ (username + password)
              ▼
┌──────────────────────────────┐
│      AuthController          │
│  - Recibe login              │
│  - Llama a AuthenticationMgr │
└─────────────┬────────────────┘
              │
              │ Spring Security
              ▼
┌──────────────────────────────┐
│   AuthenticationManager      │
│  - Verifica username/password│
│  - Llama a UserDetailsService│
└─────────────┬────────────────┘
              │
              │ Devuelve Authentication object
              ▼
┌──────────────────────────────┐
│   JwtTokenProvider           │
│  - Genera token JWT          │
│  - Firma con secreto HMAC-SHA│
│  - Establece expiración      │
└─────────────┬────────────────┘
              │
              │ Token JWT
              ▼
┌──────────────────────────────┐
│   Cliente                     │
│  - Guarda JWT (localStorage, │
│    memoria, etc.)             │
└─────────────┬────────────────┘
              │
              │ Envío de request protegido
              │ con cabecera:
              │ Authorization: Bearer <token>
              ▼
┌──────────────────────────────┐
│ JwtAuthenticationFilter      │
│  - Intercepta la request      │
│  - Extrae token de Authorization
│  - Valida token con JwtTokenProvider
│  - Si válido: carga usuario con UserDetailsService
│  - Inserta Authentication en SecurityContext
└─────────────┬────────────────┘
              │
              │ Request autenticada
              ▼
┌──────────────────────────────┐
│  SecurityContext de Spring   │
│  - Contiene Authentication   │
│  - Permite acceder a endpoints
└─────────────┬────────────────┘
              │
              ▼
┌──────────────────────────────┐
│      API Controller           │
│  - /api/comics, /api/publishers, etc.
│  - Solo se ejecuta si SecurityContext
│    tiene Authentication válida
└──────────────────────────────┘

*/