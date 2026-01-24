package com.whitechapel.comics_collection_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Clase responsable de generar, validar y extraer información de tokens JWT.
 *
 * Funciones principales:
 *  - Generar JWT para usuarios autenticados
 *  - Validar tokens recibidos en requests
 *  - Extraer información del token (username)
 *
 * Buenas prácticas:
 *  - Secreto seguro y configurable
 *  - Manejo de excepciones con log
 *  - Comentarios educativos
 */
@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // TODO: Cambiar por un secreto largo y seguro, preferiblemente en application.properties
    private final String jwtSecret = "cambia_este_secreto_super_largo_y_seguro_para_produccion_2026";

    // Duración del token en milisegundos (24 horas)
    private final long jwtExpirationMs = 86400000;

    /**
     * Genera un token JWT a partir de la autenticación del usuario.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(username)      // Identificador del usuario
                .setIssuedAt(now)          // Fecha de creación
                .setExpiration(expiryDate) // Fecha de expiración
                .signWith(key, SignatureAlgorithm.HS256) // Firma con HMAC-SHA256
                .compact();
    }

    /**
     * Extrae el username del token JWT.
     */
    public String getUsernameFromJwt(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Valida un token JWT.
     */
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token JWT mal formado: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT no soportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token JWT vacío o nulo: {}", e.getMessage());
        } catch (SecurityException e) {
            logger.error("Firma JWT inválida: {}", e.getMessage());
        }
        return false;
    }
}
