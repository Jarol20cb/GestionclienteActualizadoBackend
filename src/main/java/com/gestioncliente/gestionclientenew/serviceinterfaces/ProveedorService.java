package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    void insert(Proveedor proveedor);
    List<Proveedor> list();
    void delete(int proveedorId);
    Proveedor listId(int proveedorId);
    List<Proveedor> findByUsername(String username);
}
