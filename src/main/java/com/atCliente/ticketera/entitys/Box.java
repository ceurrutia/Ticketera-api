package com.atCliente.ticketera.entitys;

import com.atCliente.ticketera.enums.BoxEstado;
import com.atCliente.ticketera.enums.BoxType;
import jakarta.persistence.*;

@Entity
@Table(name = "boxes")
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "box_type")
    private BoxType type;

    @Enumerated(EnumType.STRING)
    private BoxEstado estado;

    //constructor vacio

    public Box() {
    }

    //constructor con parametros

    public Box(Long id, BoxType type, BoxEstado estado) {
        this.id = id;
        this.type = type;
        this.estado = estado;
    }

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BoxType getType() {
        return type;
    }

    public void setType(BoxType type) {
        this.type = type;
    }

    public BoxEstado getEstado() {
        return estado;
    }

    public void setEstado(BoxEstado estado) {
        this.estado = estado;
    }
}
