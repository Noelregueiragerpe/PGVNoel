
package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pgv.restaurante.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}
