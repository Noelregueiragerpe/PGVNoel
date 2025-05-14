package com.pgv.restaurante.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Cabeza")
public class Cabeza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCabeza;

    private String nombre;

   @Column(length = 100000)
    private String codigo;


    public Cabeza() {}

    public Long getId() {
        return idCabeza;
    }

    public void setId(Long idCabeza) {
        this.idCabeza = idCabeza;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

     public String getCodigo() {
        return codigo;
    }
    
    
    



}
