package com.gestioncliente.gestionclientenew.entities;

import javax.persistence.*;

@Entity
@Table(name = "Services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @Column(name = "service", nullable = false, length = 20)
    private String service;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    public Services() {
    }

    public Services(int serviceId, String service, String description, String username) {
        this.serviceId = serviceId;
        this.service = service;
        this.description = description;
        this.username = username;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}