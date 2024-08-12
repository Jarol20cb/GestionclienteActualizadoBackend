package com.gestioncliente.gestionclientenew.entities;

import javax.persistence.*;

@Entity
@Table(name = "Proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proveedorId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "username", nullable = false)
    private String username;

    // Otros campos relevantes
    // Getters y setters

    public Proveedor() {
    }

    public Proveedor(int proveedorId, String nombre, String username) {
        this.proveedorId = proveedorId;
        this.nombre = nombre;
        this.username = username;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}