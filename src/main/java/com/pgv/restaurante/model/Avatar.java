package com.pgv.restaurante.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Avatar")
public class Avatar {
    
    @ManyToOne
    @JoinColumn(name = "idcabeza") 
    private Cabeza cabeza;

    @ManyToOne
    @JoinColumn(name = "idcuerpo") 
    private Cuerpo cuerpo;

    @OneToOne
    @JoinColumn(name = "idusuario", unique = true)
    private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idavatar;

    public Avatar() {}

    public Long getId() {
        return idavatar;
    }

   public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
}
    public void setId(Long idavatar) {
        this.idavatar = idavatar;
    }

  public Cabeza getCabeza() {
        return cabeza;
    }

    public void setCabeza(Cabeza cabeza) {
        this.cabeza = cabeza;
    }

public Cuerpo getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Cuerpo cuerpo) {
        this.cuerpo = cuerpo;
    }

}
