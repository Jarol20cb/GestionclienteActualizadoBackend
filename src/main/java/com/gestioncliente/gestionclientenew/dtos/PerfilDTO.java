package com.gestioncliente.gestionclientenew.dtos;

import com.gestioncliente.gestionclientenew.entities.Services;
import com.gestioncliente.gestionclientenew.entities.Proveedor;

import java.util.Date;

public class PerfilDTO {
    private int perfilId;
    private Services service;
    private Proveedor proveedor;
    private String correo;
    private String contrasena;
    private Date fechainicio;
    private Date fechafin;
    private int limiteUsuarios;
    private int usuariosActuales;
    private int usuariosDisponibles;

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
    }

    public int getUsuariosActuales() {
        return usuariosActuales;
    }

    public void setUsuariosActuales(int usuariosActuales) {
        this.usuariosActuales = usuariosActuales;
    }

    public int getUsuariosDisponibles() {
        return usuariosDisponibles;
    }

    public void setUsuariosDisponibles(int usuariosDisponibles) {
        this.usuariosDisponibles = usuariosDisponibles;
    }
}
