package com.gestioncliente.gestionclientenew.repositories;
import com.gestioncliente.gestionclientenew.entities.MensajesPersonalizados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajesPersonalizadosRepository extends JpaRepository<MensajesPersonalizados, Integer> {
    List<MensajesPersonalizados> findByUsername(String username);
}
