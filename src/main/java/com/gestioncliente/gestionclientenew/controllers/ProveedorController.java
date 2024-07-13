package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.ProveedorDTO;
import com.gestioncliente.gestionclientenew.entities.Proveedor;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ProveedorService;
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
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody ProveedorDTO dto) {
        ModelMapper m = new ModelMapper();
        Proveedor p = m.map(dto, Proveedor.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        proveedorService.insert(p);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ProveedorDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return proveedorService.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ProveedorDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Proveedor proveedor = proveedorService.listId(id);
        if (proveedor != null && proveedor.getUsername().equals(username)) {
            proveedorService.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ProveedorDTO listarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Proveedor proveedor = proveedorService.listId(id);
        if (proveedor != null && proveedor.getUsername().equals(username)) {
            ModelMapper m = new ModelMapper();
            ProveedorDTO dto = m.map(proveedor, ProveedorDTO.class);
            return dto;
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void actualizar(@RequestBody ProveedorDTO dto) {
        ModelMapper m = new ModelMapper();
        Proveedor p = m.map(dto, Proveedor.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        proveedorService.insert(p);
    }
}
