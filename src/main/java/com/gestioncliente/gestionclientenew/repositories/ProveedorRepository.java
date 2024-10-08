package com.gestioncliente.gestionclientenew.repositories;

import com.gestioncliente.gestionclientenew.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    List<Proveedor> findByUsername(String username);
}
