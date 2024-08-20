package com.gestioncliente.gestionclientenew.dtos.referenciasociodto;

import com.gestioncliente.gestionclientenew.entities.Services;

import java.util.Date;

public class CustomerServiceWithoutSocioDTO {
    private int idcs;
    private String name;
    private Services services;
    private Date fechainicio;
    private Date fechafin;
    private String estado;


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
