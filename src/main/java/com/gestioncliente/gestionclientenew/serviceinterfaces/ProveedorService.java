package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Proveedor;

import java.util.List;

public interface ProveedorService {
    public void insert(Proveedor proveedor);
    public List<Proveedor> list();
    public void delete(int proveedorId);
    public Proveedor listId(int proveedorId);
}
