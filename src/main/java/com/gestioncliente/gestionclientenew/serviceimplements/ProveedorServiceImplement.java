package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.Proveedor;
import com.gestioncliente.gestionclientenew.repositories.ProveedorRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImplement implements ProveedorService {
    @Autowired
    private ProveedorRepository pr;

    @Override
    public void insert(Proveedor proveedor) {
        pr.save(proveedor);
    }

    @Override
    public List<Proveedor> list() {
        return pr.findAll();
    }

    @Override
    public void delete(int proveedorId) {
        pr.deleteById(proveedorId);
    }

    @Override
    public Proveedor listId(int proveedorId) {
        return pr.findById(proveedorId).orElse(new Proveedor());
    }

    @Override
    public List<Proveedor> findByUsername(String username) {
        return pr.findByUsername(username);
    }
}
