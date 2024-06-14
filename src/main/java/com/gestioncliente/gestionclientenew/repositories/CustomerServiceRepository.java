package com.gestioncliente.gestionclientenew.repositories;
import com.gestioncliente.gestionclientenew.entities.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService,Integer> {
}
