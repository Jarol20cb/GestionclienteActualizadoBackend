package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Perfil;

import java.util.List;

public interface PerfilService {
    void insert(Perfil perfil);
    List<Perfil> list();
    void delete(int perfilId);
    Perfil listId(int perfilId);
    List<Perfil> findByServiceAndAvailable(int serviceId);
    List<Perfil> findByServiceId(int serviceId);
    List<Perfil> findByUsername(String username);
}
