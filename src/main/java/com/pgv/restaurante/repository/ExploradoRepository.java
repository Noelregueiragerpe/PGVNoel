package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pgv.restaurante.model.Explorado;
import com.pgv.restaurante.model.ExploradoId;

import java.util.List;

public interface ExploradoRepository extends JpaRepository<Explorado, ExploradoId> {
    List<Explorado> findByIdIdUsuario(Long idUsuario);
}
