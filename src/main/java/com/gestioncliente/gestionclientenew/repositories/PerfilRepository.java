package com.gestioncliente.gestionclientenew.repositories;

import com.gestioncliente.gestionclientenew.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    @Query("SELECT p FROM Perfil p WHERE p.service.serviceId = :serviceId AND p.usuariosDisponibles > 0")
    List<Perfil> findByServiceAndAvailable(int serviceId);

    @Query("SELECT p FROM Perfil p WHERE p.service.serviceId = :serviceId")
    List<Perfil> findByServiceId(@Param("serviceId") int serviceId);

    @Query("SELECT p FROM Perfil p WHERE p.username = :username")
    List<Perfil> findByUsername(@Param("username") String username);
}
