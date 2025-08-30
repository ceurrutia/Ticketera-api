package com.atCliente.ticketera.DTO;

import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.enums.Tipo_consulta;

import java.util.Date;

public class TicketDTO {
    private Long id;

    private String clienteNombre;
    private String clienteDni;
    private String clienteEmail;

    private Tipo_consulta tipoConsulta;
    private Date fechaHora;
    private Estado estado;

    //constructor vacio
    public TicketDTO() {
    }

    //constructor con parametros

    public TicketDTO(Long id, String clienteNombre, String clienteDni, String clienteEmail,
                     Tipo_consulta tipoConsulta, Date fechaHora, Estado estado) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.clienteDni = clienteDni;
        this.clienteEmail = clienteEmail;
        this.tipoConsulta = tipoConsulta;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    //Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDni() {
        return clienteDni;
    }

    public void setClienteDni(String clienteDni) {
        this.clienteDni = clienteDni;
    }

    public String getClienteEmail() {
        return clienteEmail;
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }

    public Tipo_consulta getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(Tipo_consulta tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
