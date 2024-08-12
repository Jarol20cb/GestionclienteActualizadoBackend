package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.MensajesPersonalizados;

import java.util.List;

public interface MensajesPersonalizadosService {
    void insert(MensajesPersonalizados mensajesPersonalizados);
    List<MensajesPersonalizados> list();
    void delete(int id);
    MensajesPersonalizados listId(int id);
    List<MensajesPersonalizados> findByUsername(String username);
}
