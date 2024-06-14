package com.gestioncliente.gestionclientenew.serviceinterfaces;
import com.gestioncliente.gestionclientenew.entities.Services;

import java.util.List;

public interface ServicesService {
    public void insert(Services services);
    public List<Services> list();
    public void delete(int serviceId);
    public Services listId(int serviceId);
}
