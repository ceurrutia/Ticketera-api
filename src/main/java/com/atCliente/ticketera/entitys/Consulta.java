package com.atCliente.ticketera.entitys;

import com.atCliente.ticketera.enums.Tipo_consulta;
import jakarta.persistence.*;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo_consulta tipoConsulta;

    //constructor vacio
    public Consulta() {
    }

    //constructor parametros
    public Consulta(Tipo_consulta tipoConsulta, Long id) {
        this.tipoConsulta = tipoConsulta;
        this.id = id;
    }

    //getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tipo_consulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(Tipo_consulta tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }
}
