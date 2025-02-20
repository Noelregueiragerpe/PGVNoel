package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgv.restaurante.model.Lugar;

public interface LugarRepository extends JpaRepository<Lugar, Long> {
}

