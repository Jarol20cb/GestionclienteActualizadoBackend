package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.Users;

import java.util.List;
import java.util.Optional;

public interface IMessageService {
    List<Message> getAllMessages();
    List<Message> getMessagesByUser(Users user);
    Message saveMessage(Message message);
    void deleteMessage(Long id);
    Optional<Message> findById(Long id);
}