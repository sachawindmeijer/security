package com.example.fullsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Deze configuratieklasse zorgt ervoor dat je Spring Boot applicatie cross-origin verzoeken accepteert van alle domeinen en dat deze verzoeken alle veelgebruikte HTTP-methoden kunnen gebruiken. Dit is vooral nuttig als je front-end applicatie gehost wordt op een ander domein dan je back-end API, en je wilt dat de front-end probleemloos met de back-end kan communiceren.
public class GlobalCorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }
}
