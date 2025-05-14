package com.pgv.restaurante.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Cuerpo")
public class Cuerpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuerpo;

    private String nombre;
    
    @Column(length = 100000)
    private String codigo;

    public Cuerpo() {}

    public Long getId() {
        return idCuerpo;
    }

    public void setId(Long idCuerpo) {
        this.idCuerpo = idCuerpo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

     public String getCodigo() {
        return codigo;
    }
    


}
