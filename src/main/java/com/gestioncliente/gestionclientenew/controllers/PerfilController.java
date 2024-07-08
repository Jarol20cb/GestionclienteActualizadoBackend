package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.PerfilDTO;
import com.gestioncliente.gestionclientenew.entities.Perfil;
import com.gestioncliente.gestionclientenew.serviceinterfaces.PerfilService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @PostMapping
    public void registrar(@RequestBody PerfilDTO dto) {
        ModelMapper m = new ModelMapper();
        Perfil p = m.map(dto, Perfil.class);
        perfilService.insert(p);
    }

    @GetMapping
    public List<PerfilDTO> listar() {
        return perfilService.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, PerfilDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        perfilService.delete(id);
    }

    @GetMapping("/{id}")
    public PerfilDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        PerfilDTO dto = m.map(perfilService.listId(id), PerfilDTO.class);
        return dto;
    }

    @PutMapping
    public void actualizar(@RequestBody PerfilDTO dto) {
        ModelMapper m = new ModelMapper();
        Perfil p = m.map(dto, Perfil.class);
        perfilService.insert(p);
    }
}
