package com.pgv.restaurante.model;

import jakarta.persistence.*;

@Entity
public class Explorado {

    @EmbeddedId
    private ExploradoId id;

    private boolean favorito;

    @Transient
    private String nombre_usuario; // Campo transitorio para nombre de usuario

    @Transient
    private String nombre_lugar; // Campo transitorio para nombre de lugar

    // Getters y setters
    public ExploradoId getId() {
        return id;
    }

    public void setId(ExploradoId id) {
        this.id = id;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_lugar() {
        return nombre_lugar;
    }

    public void setNombre_lugar(String nombre_lugar) {
        this.nombre_lugar = nombre_lugar;
    }
}
