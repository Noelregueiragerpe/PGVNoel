
package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgv.restaurante.model.Cuerpo;

public interface CuerposRepository extends JpaRepository<Cuerpo, Long> {
}
