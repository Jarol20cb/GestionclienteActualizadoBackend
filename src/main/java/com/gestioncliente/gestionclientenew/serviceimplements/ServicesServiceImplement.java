package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.Services;
import com.gestioncliente.gestionclientenew.repositories.ServicesRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesServiceImplement implements ServicesService {
    @Autowired
    private ServicesRepository s;

    @Override
    public void insert(Services services) {
        s.save(services);
    }

    @Override
    public List<Services> list() {
        return s.findAll();
    }

    @Override
    public void delete(int serviceId) {
        s.deleteById(serviceId);
    }

    @Override
    public Services listId(int serviceId) {
        return s.findById(serviceId).orElse(new Services());
    }

    @Override
    public List<Services> findByUsername(String username) {
        return s.findByUsername(username);
    }
}
