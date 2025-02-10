package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgv.restaurante.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}

