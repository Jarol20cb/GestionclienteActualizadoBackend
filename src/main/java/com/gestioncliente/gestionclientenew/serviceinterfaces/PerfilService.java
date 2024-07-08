package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Perfil;

import java.util.List;

public interface PerfilService {
    public void insert(Perfil perfil);
    public List<Perfil> list();
    public void delete(int perfilId);
    public Perfil listId(int perfilId);
    List<Perfil> findByServiceAndAvailable(int serviceId);
}
