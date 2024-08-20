package com.gestioncliente.gestionclientenew.serviceinterfaces;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    List<Message> getAllMessages();
    Message saveMessage(Message message);
    void deleteMessage(Long id);
    Optional<Message> findById(Long id);
    List<Message> findByUsername(String username);
}