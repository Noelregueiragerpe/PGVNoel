package com.pgv.restaurante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgv.restaurante.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioId(Long usuarioId);

}
