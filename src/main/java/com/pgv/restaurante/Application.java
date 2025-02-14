package com.pgv.restaurante;

import com.pgv.restaurante.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())  // Habilitar CORS
            .csrf(AbstractHttpConfigurer::disable)  // Deshabilitar CSRF
            .authorizeHttpRequests(authz -> authz
                // Permitir login y registro sin autenticación (POST)
                .requestMatchers("/api/usuario").permitAll()
                    .requestMatchers("/api/usuario/login").permitAll()
                    .requestMatchers("/api/usuario/logout/**").permitAll()
                // Permitir también métodos GET en /api/usuario si es necesario
                .requestMatchers(HttpMethod.GET, "/api/usuario").permitAll()
                // Requiere autenticación para todas las demás rutas
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }
}
