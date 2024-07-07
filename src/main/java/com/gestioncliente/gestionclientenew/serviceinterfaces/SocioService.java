package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.Socio;

import java.util.List;

public interface SocioService {
    void insert(Socio socio);
    List<Socio> list();
    void delete(int id);
    Socio listId(int id);
}
