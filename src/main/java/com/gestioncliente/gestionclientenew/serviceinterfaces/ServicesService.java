package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Services;

import java.util.List;

public interface ServicesService {
    void insert(Services services);
    List<Services> list();
    void delete(int serviceId);
    Services listId(int serviceId);
    List<Services> findByUsername(String username);
}
