package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.CustomerServiceDTO;
import com.gestioncliente.gestionclientenew.entities.CustomerService;
import com.gestioncliente.gestionclientenew.serviceinterfaces.CustomerServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customerservices")
public class CustomerServiceController {
    @Autowired
    private CustomerServiceService a;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody CustomerServiceDTO dto) {
        ModelMapper m = new ModelMapper();
        CustomerService c = m.map(dto, CustomerService.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        c.setUsername(username);
        a.insert(c);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void Update(@RequestBody CustomerServiceDTO dto) {
        ModelMapper m = new ModelMapper();
        CustomerService c = m.map(dto, CustomerService.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        c.setUsername(username);
        a.insert(c);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<CustomerServiceDTO> Listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return a.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, CustomerServiceDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        CustomerService customerService = a.listId(id);
        if (customerService != null && customerService.getUsername().equals(username)) {
            a.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomerServiceDTO ListarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        CustomerService customerService = a.listId(id);
        if (customerService != null && customerService.getUsername().equals(username)) {
            ModelMapper m = new ModelMapper();
            CustomerServiceDTO dto = m.map(customerService, CustomerServiceDTO.class);
            return dto;
        }
        return null;
    }
}
