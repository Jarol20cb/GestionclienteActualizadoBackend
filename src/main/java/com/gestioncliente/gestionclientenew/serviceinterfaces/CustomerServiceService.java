package com.gestioncliente.gestionclientenew.serviceinterfaces;

import com.gestioncliente.gestionclientenew.entities.CustomerService;

import java.util.List;

public interface CustomerServiceService {
    void insert(CustomerService cuser);
    List<CustomerService> list();
    void delete(int idcs);
    CustomerService listId(int idcs);
    List<CustomerService> findByUsername(String username);
}
