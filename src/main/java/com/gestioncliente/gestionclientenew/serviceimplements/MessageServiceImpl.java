package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IMessageRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IMessageRepository messageRepo;

    @Override
    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    @Override
    public List<Message> getMessagesByUser(Users user) {
        return messageRepo.findByUser(user);
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        messageRepo.deleteById(id);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepo.findById(id);
    }

}