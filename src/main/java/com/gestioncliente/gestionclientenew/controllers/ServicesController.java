package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.PerfilDTO;
import com.gestioncliente.gestionclientenew.dtos.ServicesDTO;
import com.gestioncliente.gestionclientenew.entities.Services;
import com.gestioncliente.gestionclientenew.serviceinterfaces.PerfilService;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ServicesService;
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
@RequestMapping("/services")
public class ServicesController {

    @Autowired
    private ServicesService a;
    @Autowired
    private PerfilService perfilService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody ServicesDTO dto) {
        ModelMapper m = new ModelMapper();
        Services c = m.map(dto, Services.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        c.setUsername(username);
        a.insert(c);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ServicesDTO> Listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return a.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ServicesDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Services service = a.listId(id);
        if (service != null && service.getUsername().equals(username)) {
            a.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ServicesDTO ListarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Services service = a.listId(id);
        if (service != null && service.getUsername().equals(username)) {
            ModelMapper m = new ModelMapper();
            ServicesDTO dto = m.map(service, ServicesDTO.class);
            return dto;
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void Update(@RequestBody ServicesDTO dto) {
        ModelMapper m = new ModelMapper();
        Services c = m.map(dto, Services.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        c.setUsername(username);
        a.insert(c);
    }

    @GetMapping("/{id}/perfiles")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PerfilDTO> listarPerfilesPorServicio(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Services service = a.listId(id);
        if (service != null && service.getUsername().equals(username)) {
            return perfilService.findByServiceId(id).stream().map(perfil -> {
                ModelMapper modelMapper = new ModelMapper();
                return modelMapper.map(perfil, PerfilDTO.class);
            }).collect(Collectors.toList());
        }
        return null;
    }
}
