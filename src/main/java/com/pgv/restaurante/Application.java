package com.pgv.restaurante;

import com.pgv.restaurante.model.Cabeza;
import com.pgv.restaurante.model.Cuerpo;
import com.pgv.restaurante.model.Lugar;
import com.pgv.restaurante.model.Pelicula;
import com.pgv.restaurante.repository.CabezasRepository;
import com.pgv.restaurante.repository.CuerposRepository;
import com.pgv.restaurante.repository.LugarRepository;
import com.pgv.restaurante.repository.PeliculaRepository;
import com.pgv.restaurante.security.JWTAuthorizationFilter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(PeliculaRepository peliculaRepository, LugarRepository lugarRepository, CabezasRepository cabezasRepository, CuerposRepository cuerposRepository) {
        return args -> {
            if (peliculaRepository.count() == 0) { // Solo si no hay datos
                Pelicula[] peliculas = new Pelicula[]{
                        new Pelicula("Breakfast at Tiffany's", "Romance", 1961),
                        new Pelicula("Gladiator", "Acción", 2000),
                        new Pelicula("The Da Vinci Code", "Misterio", 2006),
                        new Pelicula("Harry Potter and the Philosopher's Stone", "Fantasía", 2001),
                        new Pelicula("Full House", "Comedia", 1987),
                        new Pelicula("Mission: Impossible - Ghost Protocol", "Acción", 2011),
                        new Pelicula("The Best Exotic Marigold Hotel", "Comedia dramática", 2011),
                        new Pelicula("Casino Royale", "Acción", 2006),
                        new Pelicula("The Last Samurai", "Acción", 2003),
                        new Pelicula("Marie Antoniette", "Drama histórico", 2006),
                        new Pelicula("Clash of the Titans", "Fantasía", 2010),
                        new Pelicula("Alatriste", "Drama histórico", 2006),
                        new Pelicula("Vicy Cristina Barcelona", "Romance", 2008),
                        new Pelicula("El Cid", "Épico histórico", 1961),
                        new Pelicula("Fast & Furious 6", "Acción", 2013),
                        new Pelicula("Tomorrowland", "Ciencia ficción", 2015)
                };
                Lugar[] lugares = new Lugar[]{
                        new Lugar("Parque", "New York", 40.785091, -73.968285, "Central_Park_Breakfast_at_Tiffanys.jpg", "Central Park", peliculas[0]),
                        new Lugar("Monumento", "Rome", 41.890251, 12.492373, "The_Colosseum_Gladiator.webp", "The Colosseum", peliculas[1]),
                        new Lugar("Monumento", "Paris", 48.8588443, 2.2943506, "Eiffel_Tower_The_Da_Vinci_Code.jpg", "Eiffel Tower", peliculas[2]),
                        new Lugar("Ficción", "Highlands", 56.4981, -3.7437, "Hogwarts_Castle_Harry_Potter_and_the_Philosophers_Stone.jpg", "Hogwarts Castle", peliculas[3]),
                        new Lugar("Urbano", "San Francisco", 37.7764, -122.4346, "Alamo_Square_Full_House.jpg", "Alamo Square", peliculas[4]),
                        new Lugar("Histórico", "Moscow", 55.7558, 37.6176, "Red_Square_Mission_Impossible_Ghost_Protocol.jpg", "Red Square", peliculas[5]),
                        new Lugar("Patrimonio", "Agra", 27.1751, 78.0421, "Taj_Mahal_The_Best_Exotic_Marigold_Hotel.webp", "Taj Mahal", peliculas[6]),
                        new Lugar("Natural", "Lombardy", 45.9590, 9.2878, "Lake_Como_Casino_Royale.webp", "Lake Como", peliculas[7]),
                        new Lugar("Montaña", "Honshu", 35.3606, 138.7274, "Mount_Fuji_The_Last_Samurai.jpg", "Mount_Fuji", peliculas[8]),
                        new Lugar("Palacio", "Versailles", 48.8049, 2.1204, "Palace_of_Versailles_Marie_Antoinette.webp", "Palace of Versailles", peliculas[9]),
                        new Lugar("Desierto", "Maspalomas", 27.7513, -15.5762, "Dunas_de_Maspalomas_Clash_of_the_Titans.jpg", "Dunas de Maspalomas", peliculas[10]),
                        new Lugar("Histórico", "Madrid", 40.4154, -3.7074, "Plaza_Mayor_de_Madrid_Alatriste.jpg", "Plaza Mayor", peliculas[11]),
                        new Lugar("Parque", "Barcelona", 41.4145, 2.1527, "Parque_Guell_Vicky_Cristina_Barcelona.jpg", "Parque Guell", peliculas[12]),
                        new Lugar("Patrimonio", "Granada", 37.1761, -3.5881, "La_Alhambra_El_Cid.jpg", "La Alhambra", peliculas[13]),
                        new Lugar("Volcánico", "Tenerife", 28.2712, -16.6425, "Teide_National_Park_Fast_and_Furious_6.jpg", "Teide National Park", peliculas[14]),
                        new Lugar("Ciencia", "Valencia", 39.4551, -0.3549, "Ciudad_de_las_Artes_y_las_Ciencias_Tomorrowland.jpg", "Ciudad de las Artes y las Ciencias", peliculas[15]),
                };
                Cabeza[] cabezas = new Cabeza[]{
                    new Cabeza("cabezaSimple", ""),
                    new Cabeza("cabezaLotr", ""),
                    new Cabeza("cabezaIron", ""),
                };
                Cuerpo[] cuerpos = new Cuerpo[]{
                    new Cuerpo("cuerpoSimple", ""),
                    new Cuerpo("cuerpoLotr", ""),
                    new Cuerpo("cuerpoIron", ""),
                };
                peliculaRepository.saveAll(Arrays.asList(peliculas));
                System.out.println("📀 Base de datos inicializada con películas.");
                lugarRepository.saveAll(Arrays.asList(lugares));
                System.out.println("📀 Base de datos inicializada con lugares.");
                cabezasRepository.saveAll(Arrays.asList(cabezas));
                System.out.println("📀 Base de datos inicializada con cabezas.");
                cuerposRepository.saveAll(Arrays.asList(cuerpos));
                System.out.println("📀 Base de datos inicializada con cuerpos.");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/api/usuario/login", "/api/usuario").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuario").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
