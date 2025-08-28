package com.atCliente.ticketera.DTO;

import com.atCliente.ticketera.enums.Tipo_consulta;

public class ConsultaDTO {
    private Long id;
    private Tipo_consulta tipoConsulta;

    // Constructor vacío
    public ConsultaDTO() {}

    // Constructor con parámetros
    public ConsultaDTO(Long id, Tipo_consulta tipoConsulta) {
        this.id = id;
        this.tipoConsulta = tipoConsulta;
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
