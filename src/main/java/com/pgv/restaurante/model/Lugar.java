package com.pgv.restaurante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lugar")
public class Lugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ciudad;
    private double coordenadasx;
    private double coordenadasy;
    private String categoria;
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_pelicula") 
    private Pelicula pelicula;

    public Lugar() {}

    public Lugar(String categoria, String ciudad, double coordenadasx, double coordenadasy, String imagen, String nombre, Pelicula pelicula) {
        this.categoria = categoria;
        this.ciudad = ciudad;
        this.coordenadasx = coordenadasx;
        this.coordenadasy = coordenadasy;
        this.imagen = imagen;
        this.nombre = nombre;
        this.pelicula = pelicula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getCoordenadasx() {
        return coordenadasx;
    }

    public void setCoordenadasx(double coordenadasx) {
        this.coordenadasx = coordenadasx;
    }

    public double getCoordenadasy() {
        return coordenadasy;
    }

    public void setCoordenadasy(double coordenadasy) {
        this.coordenadasy = coordenadasy;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() { return this.imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
}
