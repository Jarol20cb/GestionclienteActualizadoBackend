package com.gestioncliente.gestionclientenew.dtos;

import com.gestioncliente.gestionclientenew.entities.Services;
import com.gestioncliente.gestionclientenew.entities.Perfil;
import com.gestioncliente.gestionclientenew.entities.Socio;

import java.util.Date;

public class CustomerServiceDTO {
    private int idcs;
    private String name;
    private Services services;
    private Perfil perfil;
    private Socio socio; // Agregar campo socio
    private Date fechainicio;
    private Date fechafin;
    private String estado;

    // Getters y Setters

    public int getIdcs() {
        return idcs;
    }

    public void setIdcs(int idcs) {
        this.idcs = idcs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
