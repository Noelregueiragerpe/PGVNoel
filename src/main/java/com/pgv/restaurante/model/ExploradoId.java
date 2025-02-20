package com.pgv.restaurante.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExploradoId implements Serializable {
    private Long idUsuario;
    private Long idLugar;

    public ExploradoId() {}

    public ExploradoId(Long idUsuario, Long idLugar) {
        this.idUsuario = idUsuario;
        this.idLugar = idLugar;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdLugar() {
        return idLugar;
    }

    public void setIdLugar(Long idLugar) {
        this.idLugar = idLugar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExploradoId that = (ExploradoId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
                Objects.equals(idLugar, that.idLugar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idLugar);
    }
}
