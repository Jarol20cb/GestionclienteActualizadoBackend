package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.Socio;
import com.gestioncliente.gestionclientenew.repositories.SocioRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioServiceImplement implements SocioService {
    @Autowired
    private SocioRepository socioRepository;

    @Override
    public void insert(Socio socio) {
        socioRepository.save(socio);
    }

    @Override
    public List<Socio> list() {
        return socioRepository.findAll();
    }

    @Override
    public void delete(int id) {
        socioRepository.deleteById(id);
    }

    @Override
    public Socio listId(int id) {
        return socioRepository.findById(id).orElse(null);
    }
}
