package com.whitechapel.comics_collection_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.ui.Model;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Redirecting to <a href='/swagger-ui.html'>Swagger UI</a>";
    }
}