package com.gestioncliente.gestionclientenew.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int perfilId;

    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false)
    private Services service;

    @ManyToOne
    @JoinColumn(name = "proveedorId", nullable = true)
    private Proveedor proveedor;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    @Column(name = "fechainicio", nullable = false)
    private Date fechainicio;

    @Column(name = "fechafin", nullable = false)
    private Date fechafin;

    @Column(name = "limiteUsuarios", nullable = false)
    private int limiteUsuarios;

    @Column(name = "usuariosActuales", nullable = false)
    private int usuariosActuales = 0;

    @Column(name = "usuariosDisponibles", nullable = false)
    private int usuariosDisponibles;

    @Column(name = "username", nullable = false)
    private String username;

    public Perfil() {
        this.usuariosDisponibles = this.limiteUsuarios - this.usuariosActuales;
    }

    public Perfil(int perfilId, Services service, Proveedor proveedor, String correo, String contrasena, Date fechainicio, Date fechafin, int limiteUsuarios, int usuariosActuales, int usuariosDisponibles, String username) {
        this.perfilId = perfilId;
        this.service = service;
        this.proveedor = proveedor;
        this.correo = correo;
        this.contrasena = contrasena;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.limiteUsuarios = limiteUsuarios;
        this.usuariosActuales = usuariosActuales;
        this.usuariosDisponibles = limiteUsuarios - usuariosActuales;
        this.username = username;
    }

    // Getters y Setters
    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public int getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public void setLimiteUsuarios(int limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
        this.usuariosDisponibles = limiteUsuarios - this.usuariosActuales;
    }

    public int getUsuariosActuales() {
        return usuariosActuales;
    }

    public void setUsuariosActuales(int usuariosActuales) {
        this.usuariosActuales = usuariosActuales;
        this.usuariosDisponibles = this.limiteUsuarios - usuariosActuales;
    }

    public int getUsuariosDisponibles() {
        return usuariosDisponibles;
    }

    public void setUsuariosDisponibles(int usuariosDisponibles) {
        this.usuariosDisponibles = usuariosDisponibles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
