package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.SocioDTO;
import com.gestioncliente.gestionclientenew.dtos.referenciasociodto.CustomerServiceWithoutSocioDTO;
import com.gestioncliente.gestionclientenew.dtos.referenciasociodto.SocioCustomerServiceDTO;
import com.gestioncliente.gestionclientenew.entities.Socio;
import com.gestioncliente.gestionclientenew.serviceinterfaces.SocioService;
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
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody SocioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Socio socio = modelMapper.map(dto, Socio.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        socio.setUsername(username);
        socioService.insert(socio);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<SocioDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return socioService.findByUsername(username).stream().map(socio -> {
            ModelMapper modelMapper = new ModelMapper();
            SocioDTO socioDTO = modelMapper.map(socio, SocioDTO.class);
            socioDTO.setClienteCount(socio.getCustomerServices().size()); // Set clienteCount
            return socioDTO;
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Socio socio = socioService.listId(id);
        if (socio != null && socio.getUsername().equals(username)) {
            socioService.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public SocioDTO listarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Socio socio = socioService.listId(id);
        if (socio != null && socio.getUsername().equals(username)) {
            ModelMapper modelMapper = new ModelMapper();
            SocioDTO socioDTO = modelMapper.map(socio, SocioDTO.class);
            socioDTO.setClienteCount(socio.getCustomerServices().size()); // Set clienteCount
            return socioDTO;
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void update(@RequestBody SocioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Socio socio = modelMapper.map(dto, Socio.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        socio.setUsername(username);
        socioService.insert(socio);
    }

    @GetMapping("/{id}/customerservices")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public SocioCustomerServiceDTO listarCustomerServicesPorSocio(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Socio socio = socioService.listId(id);
        if (socio != null && socio.getUsername().equals(username)) {
            ModelMapper modelMapper = new ModelMapper();
            SocioCustomerServiceDTO socioCustomerServiceDTO = new SocioCustomerServiceDTO();
            socioCustomerServiceDTO.setSocioId(socio.getSocioId());
            socioCustomerServiceDTO.setName(socio.getName());
            socioCustomerServiceDTO.setCustomerServices(
                    socio.getCustomerServices().stream().map(cs -> {
                        return modelMapper.map(cs, CustomerServiceWithoutSocioDTO.class);
                    }).collect(Collectors.toList())
            );
            return socioCustomerServiceDTO;
        }
        return null;
    }
}
