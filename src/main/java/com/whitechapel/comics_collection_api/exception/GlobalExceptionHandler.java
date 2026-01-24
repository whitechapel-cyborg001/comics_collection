package com.whitechapel.comics_collection_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejo global de excepciones para toda la aplicación.
 *
 * Función:
 *  - Captura errores de validación y otros errores no controlados
 *  - Devuelve respuestas consistentes con código HTTP adecuado
 *  - Mejora la experiencia de desarrollo y depuración
 *
 * Buenas prácticas:
 *  - Centralizar el manejo de errores en un solo lugar
 *  - No duplicar status HTTP (usar ResponseEntity)
 *  - Explicar cada paso con comentarios
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación (@Valid) en request bodies.
     *
     * Ejemplo: si faltan campos obligatorios o violan constraints
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Itera sobre cada error de campo y lo agrega al mapa
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Devuelve un JSON con { campo: mensaje_error } y status 400
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Maneja excepciones generales no controladas.
     *
     * Nota:
     *  - En producción se recomienda no exponer detalles sensibles
     *  - Se puede mejorar mostrando un mensaje genérico y logueando detalles
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        // Log para depuración (puedes usar Logger si quieres)
        ex.printStackTrace();

        // Devuelve mensaje simple y status 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Ocurrió un error interno en el servidor");
    }
}
