package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.PerfilDTO;
import com.gestioncliente.gestionclientenew.entities.Perfil;
import com.gestioncliente.gestionclientenew.serviceinterfaces.PerfilService;
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
@RequestMapping("/perfiles")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody PerfilDTO dto) {
        ModelMapper m = new ModelMapper();
        Perfil p = m.map(dto, Perfil.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        perfilService.insert(p);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<PerfilDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return perfilService.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, PerfilDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Perfil perfil = perfilService.listId(id);
        if (perfil != null && perfil.getUsername().equals(username)) {
            perfilService.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PerfilDTO listarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Perfil perfil = perfilService.listId(id);
        if (perfil != null && perfil.getUsername().equals(username)) {
            ModelMapper m = new ModelMapper();
            PerfilDTO dto = m.map(perfil, PerfilDTO.class);
            return dto;
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void actualizar(@RequestBody PerfilDTO dto) {
        ModelMapper m = new ModelMapper();
        Perfil p = m.map(dto, Perfil.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        perfilService.insert(p);
    }

    @GetMapping("/available/{serviceId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<Perfil> getAvailablePerfiles(@PathVariable int serviceId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<Perfil> perfiles = perfilService.findByServiceId(serviceId);
        if (!perfiles.isEmpty() && perfiles.get(0).getUsername().equals(username)) {
            return perfilService.findByServiceAndAvailable(serviceId);
        }
        return null;
    }
}
