
package com.pgv.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pgv.restaurante.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
