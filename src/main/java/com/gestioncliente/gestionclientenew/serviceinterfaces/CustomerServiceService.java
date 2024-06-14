package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.CustomerService;

import java.util.List;

public interface CustomerServiceService {
    public void insert(CustomerService cuser);
    public List<CustomerService> list();
    public void delete(int idcs);
    public CustomerService listId(int idcs);
}
