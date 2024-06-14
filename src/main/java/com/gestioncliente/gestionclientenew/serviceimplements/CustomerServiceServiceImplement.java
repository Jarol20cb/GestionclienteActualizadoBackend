package com.gestioncliente.gestionclientenew.serviceimplements;
import com.gestioncliente.gestionclientenew.entities.CustomerService;
import com.gestioncliente.gestionclientenew.repositories.CustomerServiceRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.CustomerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceServiceImplement implements CustomerServiceService {
    @Autowired
    private CustomerServiceRepository cs;

    @Override
    public void insert(CustomerService cuser) {
        cs.save(cuser);
    }

    @Override
    public List<CustomerService> list() {
        return cs.findAll();
    }

    @Override
    public void delete(int idcs) {
        cs.deleteById(idcs);
    }

    @Override
    public CustomerService listId(int idcs) {
        return cs.findById(idcs).orElse(new CustomerService());
    }


}
