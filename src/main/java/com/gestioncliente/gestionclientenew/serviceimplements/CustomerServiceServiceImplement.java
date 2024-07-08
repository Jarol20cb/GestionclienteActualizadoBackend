package com.gestioncliente.gestionclientenew.serviceimplements;

import com.gestioncliente.gestionclientenew.entities.CustomerService;
import com.gestioncliente.gestionclientenew.entities.Perfil;
import com.gestioncliente.gestionclientenew.repositories.CustomerServiceRepository;
import com.gestioncliente.gestionclientenew.repositories.PerfilRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceServiceImplement implements CustomerServiceService {
    @Autowired
    private CustomerServiceRepository cs;

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public void insert(CustomerService cuser) {
        Perfil perfil = perfilRepository.findById(cuser.getPerfil().getPerfilId()).orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        if (perfil.getUsuariosDisponibles() > 0) {
            perfil.setUsuariosActuales(perfil.getUsuariosActuales() + 1);
            perfil.setUsuariosDisponibles(perfil.getLimiteUsuarios() - perfil.getUsuariosActuales());
            perfilRepository.save(perfil);
            cs.save(cuser);
        } else {
            throw new RuntimeException("Límite de usuarios alcanzado para este perfil");
        }
    }

    @Override
    public void delete(int idcs) {
        CustomerService customerService = cs.findById(idcs).orElse(null);
        if (customerService != null) {
            Perfil perfil = perfilRepository.findById(customerService.getPerfil().getPerfilId()).orElse(null);
            if (perfil != null) {
                perfil.setUsuariosActuales(perfil.getUsuariosActuales() - 1);
                perfil.setUsuariosDisponibles(perfil.getLimiteUsuarios() - perfil.getUsuariosActuales());
                perfilRepository.save(perfil);
            }
            cs.deleteById(idcs);
        }
    }

    @Override
    public List<CustomerService> list() {
        return cs.findAll();
    }

    @Override
    public CustomerService listId(int idcs) {
        return cs.findById(idcs).orElse(new CustomerService());
    }
}
