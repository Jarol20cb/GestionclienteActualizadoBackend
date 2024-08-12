package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.MensajesPersonalizadosDTO;
import com.gestioncliente.gestionclientenew.entities.MensajesPersonalizados;
import com.gestioncliente.gestionclientenew.serviceinterfaces.MensajesPersonalizadosService;
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
@RequestMapping("/mensajes")
public class MensajesPersonalizadosController {

    @Autowired
    private MensajesPersonalizadosService msp;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody MensajesPersonalizadosDTO dto) {
        ModelMapper m = new ModelMapper();
        MensajesPersonalizados p = m.map(dto, MensajesPersonalizados.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        msp.insert(p);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<MensajesPersonalizadosDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return msp.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, MensajesPersonalizadosDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void eliminar(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        MensajesPersonalizados mensajesPersonalizados = msp.listId(id);
        if (mensajesPersonalizados != null && mensajesPersonalizados.getUsername().equals(username)) {
            msp.delete(id);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public MensajesPersonalizadosDTO listarId(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        MensajesPersonalizados mensajesPersonalizados = msp.listId(id);
        if (mensajesPersonalizados != null && mensajesPersonalizados.getUsername().equals(username)) {
            ModelMapper m = new ModelMapper();
            MensajesPersonalizadosDTO dto = m.map(mensajesPersonalizados, MensajesPersonalizadosDTO.class);
            return dto;
        }
        return null;
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void actualizar(@RequestBody MensajesPersonalizadosDTO dto) {
        ModelMapper m = new ModelMapper();
        MensajesPersonalizados p = m.map(dto, MensajesPersonalizados.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);
        msp.insert(p);
    }
}
