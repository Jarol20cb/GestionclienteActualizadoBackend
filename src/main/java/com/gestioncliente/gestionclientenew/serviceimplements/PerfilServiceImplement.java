package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.Perfil;
import com.gestioncliente.gestionclientenew.repositories.PerfilRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilServiceImplement implements PerfilService {
    @Autowired
    private PerfilRepository pr;

    public void insert(Perfil perfil) {
        if (perfil.getPerfilId() == 0) { // Verificar si es un nuevo perfil
            perfil.setUsuariosActuales(0);
            perfil.setUsuariosDisponibles(perfil.getLimiteUsuarios());
        }
        pr.save(perfil);
    }

    @Override
    public List<Perfil> list() {
        return pr.findAll();
    }

    @Override
    public void delete(int perfilId) {
        pr.deleteById(perfilId);
    }

    @Override
    public Perfil listId(int perfilId) {
        return pr.findById(perfilId).orElse(new Perfil());
    }
}