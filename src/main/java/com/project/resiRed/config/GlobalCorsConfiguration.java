package com.project.resiRed.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Esto aplica a todas las rutas
                .allowedOrigins("http://localhost:4200") // Origenes permitidos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Cabeceras permitidas
                .allowCredentials(true) // Si se permite enviar cookies
                .maxAge(3600); // Tiempo máximo que los navegadores deben cachear la respuesta de preflight
    }
}
