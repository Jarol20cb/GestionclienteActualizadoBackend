package com.gestioncliente.gestionclientenew.dtos.referenciasociodto;

import java.util.List;

public class SocioCustomerServiceDTO {
    private int socioId;
    private String name;
    private List<CustomerServiceWithoutSocioDTO> customerServices;

    // Getters y setters

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

    public List<CustomerServiceWithoutSocioDTO> getCustomerServices() {
        return customerServices;
    }

    public void setCustomerServices(List<CustomerServiceWithoutSocioDTO> customerServices) {
        this.customerServices = customerServices;
    }
}
