package com.whitechapel.comics_collection_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller de bienvenida.
 *
 * Función:
 *  - Cuando un usuario entra a la raíz "/", lo redirige a Swagger UI
 *  - Útil para desarrollo y pruebas de la API
 *
 * Buenas prácticas:
 *  - Usar RedirectView para un redirect real
 *  - Comentarios educativos explicando la finalidad
 */
@Controller
public class WelcomeController {

    /**
     * Redirige automáticamente a Swagger UI.
     * @return RedirectView que apunta a /swagger-ui.html
     */
    @GetMapping("/")
    public RedirectView welcome() {
        // Redirige al usuario a la documentación Swagger UI
        return new RedirectView("/swagger-ui.html");
    }
}
