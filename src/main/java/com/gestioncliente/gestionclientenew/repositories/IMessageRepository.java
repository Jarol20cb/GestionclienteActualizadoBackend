package com.gestioncliente.gestionclientenew.repositories;

import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUsername(String username);
}