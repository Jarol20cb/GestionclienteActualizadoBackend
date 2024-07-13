package com.gestioncliente.gestionclientenew.repositories;

import com.gestioncliente.gestionclientenew.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Role, Long> {
    void deleteByUserId(Long userId);
}
