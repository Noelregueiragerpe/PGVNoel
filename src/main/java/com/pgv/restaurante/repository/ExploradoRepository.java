package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pgv.restaurante.model.Explorado;
import com.pgv.restaurante.model.ExploradoId;

public interface ExploradoRepository extends JpaRepository<Explorado, ExploradoId> {
}
