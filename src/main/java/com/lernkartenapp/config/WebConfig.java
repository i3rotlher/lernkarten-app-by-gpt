package com.lernkartenapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Erlaubt CORS-Anfragen von diesem Ursprung
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubt spezifische Methoden
                .allowedHeaders("*") // Erlaubt alle Headers
                .allowCredentials(true); // Erlaubt Cookies und Credentials
    }
}
