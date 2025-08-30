package com.atCliente.ticketera.DTO;

public class ClienteDTO {
    private Long id;
    private String nombre;
    private String dni;
    private String email;

    // Constructor vacío
    public ClienteDTO() {}

    // Constructor con parámetros
    public ClienteDTO(Long id, String nombre, String dni, String email) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
    }
    //getters y setters

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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
