package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.MensajesPersonalizados;
import com.gestioncliente.gestionclientenew.repositories.MensajesPersonalizadosRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.MensajesPersonalizadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajesPersonalizadosServiceImplement implements MensajesPersonalizadosService {
    @Autowired
    private MensajesPersonalizadosRepository mp;

    @Override
    public void insert(MensajesPersonalizados mensajesPersonalizados) {
        mp.save(mensajesPersonalizados);
    }

    @Override
    public List<MensajesPersonalizados> list() {
        return mp.findAll();
    }

    @Override
    public void delete(int id) {
        mp.deleteById(id);
    }

    @Override
    public MensajesPersonalizados listId(int id) {
        return mp.findById(id).orElse(null);
    }

    @Override
    public List<MensajesPersonalizados> findByUsername(String username) {
        return mp.findByUsername(username);
    }
}
