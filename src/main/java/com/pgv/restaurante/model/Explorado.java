package com.pgv.restaurante.model;

import jakarta.persistence.*;

@Entity
public class Explorado {
    
        @EmbeddedId
        private ExploradoId id;
    
        @MapsId("idUsuario")
        @ManyToOne
        @JoinColumn(name = "id_usuario", nullable = false)
        private Usuario usuarioEntidad;
    
        @MapsId("idLugar")
        @ManyToOne
        @JoinColumn(name = "id_lugar", nullable = false)
        private Lugar lugarEntidad;
    
        private boolean favorito;
    
        // Campos adicionales
        @Transient // No se persiste en la base de datos
        private String usuario;
    
        @Transient // No se persiste en la base de datos
        private String lugar;
    
        // Getters y setters
        public ExploradoId getId() {
            return id;
        }
    
        public void setId(ExploradoId id) {
            this.id = id;
        }
    
        public Usuario getUsuarioEntidad() {
            return usuarioEntidad;
        }
    
        public void setUsuarioEntidad(Usuario usuarioEntidad) {
            this.usuarioEntidad = usuarioEntidad;
        }
    
        public Lugar getLugarEntidad() {
            return lugarEntidad;
        }
    
        public void setLugarEntidad(Lugar lugarEntidad) {
            this.lugarEntidad = lugarEntidad;
        }
    
        public boolean isFavorito() {
            return favorito;
        }
    
        public void setFavorito(boolean favorito) {
            this.favorito = favorito;
        }
    
        public String getUsuario() {
            return usuario;
        }
    
        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }
    
        public String getLugar() {
            return lugar;
        }
    
        public void setLugar(String lugar) {
            this.lugar = lugar;
        }
    }
    

