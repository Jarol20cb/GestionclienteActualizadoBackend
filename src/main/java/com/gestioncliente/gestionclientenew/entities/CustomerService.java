package com.gestioncliente.gestionclientenew.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "CustomerService")
public class CustomerService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcs;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ManyToOne
    @JoinColumn(name="serviceId", nullable=false)
    private Services services;

    @ManyToOne
    @JoinColumn(name="socioId", nullable=true)
    @JsonBackReference
    private Socio socio;
    @ManyToOne
    @JoinColumn(name="perfilId", nullable=false)
    private Perfil perfil;

    @Column(name = "fechainicio", nullable = false)
    private Date fechainicio;

    @Column(name = "fechafin", nullable = false)
    private Date fechafin;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "numerocelular", length = 15, nullable = true)
    private String numerocelular;

    public CustomerService() {
    }

    public CustomerService(int idcs, String name, Services services, Socio socio, Perfil perfil, Date fechainicio, Date fechafin, String estado, String username, String numerocelular) {
        this.idcs = idcs;
        this.name = name;
        this.services = services;
        this.socio = socio;
        this.perfil = perfil;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.estado = estado;
        this.username = username;
        this.numerocelular = numerocelular;
    }

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

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumerocelular() {
        return numerocelular;
    }

    public void setNumerocelular(String numerocelular) {
        this.numerocelular = numerocelular;
    }

    private Date calculateFechafin(Date fechainicio) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechainicio);
        cal.add(Calendar.MONTH, 1); // Add one month to the start date
        return cal.getTime();
    }

    public void updateEstadoAutomatico() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 5); // Add 5 days to the current date
        Date fechaLimite = cal.getTime();

        if (fechafin.before(new Date()) || fechafin.before(fechaLimite)) {
            this.estado = "pendiente";
        }
    }
}
