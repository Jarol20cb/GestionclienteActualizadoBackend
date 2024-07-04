package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.CustomerServiceDTO;
import com.gestioncliente.gestionclientenew.entities.CustomerService;
import com.gestioncliente.gestionclientenew.serviceinterfaces.CustomerServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customerservices")
public class CustomerServiceController {
    @Autowired
    private CustomerServiceService a;

    @PostMapping
    public void registrar(@RequestBody CustomerServiceDTO dto) {
        ModelMapper m = new ModelMapper();
        CustomerService c = m.map(dto, CustomerService.class);
        a.insert(c);
    }
    @PutMapping
    public void Update(@RequestBody CustomerServiceDTO dto) {
        ModelMapper m = new ModelMapper();
        CustomerService c = m.map(dto, CustomerService.class);
        a.insert(c);
    }

    @GetMapping
    public List<CustomerServiceDTO> Listar() {
        return a.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, CustomerServiceDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        a.delete(id);
    }

    @GetMapping("/{id}")
    public CustomerServiceDTO ListarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        CustomerServiceDTO dto = m.map((a.listId(id)), CustomerServiceDTO.class);
        return dto;
    }



}
