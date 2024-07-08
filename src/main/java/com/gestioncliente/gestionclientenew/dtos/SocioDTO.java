package com.gestioncliente.gestionclientenew.dtos;

import java.util.List;

public class SocioDTO {
    private int socioId;
    private String name;
    private int clienteCount; // Nueva propiedad
    private List<CustomerServiceDTO> customerServices;

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

    public int getClienteCount() {
        return clienteCount;
    }

    public void setClienteCount(int clienteCount) {
        this.clienteCount = clienteCount;
    }

    public List<CustomerServiceDTO> getCustomerServices() {
        return customerServices;
    }

    public void setCustomerServices(List<CustomerServiceDTO> customerServices) {
        this.customerServices = customerServices;
    }
}
