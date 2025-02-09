package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgv.restaurante.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
}

