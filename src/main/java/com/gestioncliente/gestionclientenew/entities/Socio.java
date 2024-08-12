package com.gestioncliente.gestionclientenew.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Socio")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int socioId;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "username", nullable = false)
    private String username;

    @OneToMany(mappedBy = "socio")
    @JsonManagedReference
    private List<CustomerService> customerServices;

    // Constructores, getters y setters

    public int getSocioId() {
        return socioId;
    }

    public void setSocioId(int socioId) {
        this.socioId = socioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CustomerService> getCustomerServices() {
        return customerServices;
    }

    public void setCustomerServices(List<CustomerService> customerServices) {
        this.customerServices = customerServices;
    }
}
