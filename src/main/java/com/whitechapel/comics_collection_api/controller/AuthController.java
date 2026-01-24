package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.User;
import com.whitechapel.comics_collection_api.repository.UserRepository;
import com.whitechapel.comics_collection_api.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller que expone endpoints para autenticación:
 *  - /auth/login: genera un token JWT al autenticarse
 *  - /auth/register: crea un nuevo usuario en la base de datos
 *
 * Buenas prácticas aplicadas:
 *  - Validación de entradas con @Valid
 *  - Uso de PasswordEncoder para hashear contraseñas
 *  - Devolver HTTP status correctos (200 OK, 201 Created)
 *  - Comentarios educativos para cada parte del flujo
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint para login de usuario
     * @param username Nombre de usuario
     * @param password Contraseña sin hashear
     * @return Token JWT si autenticación correcta
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {
        // 1. Autenticar al usuario con Spring Security
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Generar token JWT usando JwtTokenProvider
        String token = tokenProvider.generateToken(auth);

        // 3. Devolver token al cliente
        return ResponseEntity.ok(token);
    }

    /**
     * Endpoint para registrar un nuevo usuario
     * @param user Objeto User con username y password
     * @return Usuario creado (sin exponer password plano)
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        // 1. Validar que no exista ya el username
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }

        // 2. Hashear la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Guardar usuario en base de datos
        User savedUser = userRepository.save(user);

        // 4. Devolver usuario creado (sin password plano)
        savedUser.setPassword(null); // Nunca devuelvas la contraseña
        return ResponseEntity.status(201).body(savedUser); // 201 Created
    }
}
