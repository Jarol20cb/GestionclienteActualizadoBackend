package com.gestioncliente.gestionclientenew.controllers;
import com.gestioncliente.gestionclientenew.dtos.PerfilDTO;
import com.gestioncliente.gestionclientenew.dtos.ServicesDTO;
import com.gestioncliente.gestionclientenew.entities.Services;
import com.gestioncliente.gestionclientenew.serviceinterfaces.PerfilService;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ServicesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void registrar(@RequestBody ServicesDTO dto){
        ModelMapper m = new ModelMapper();
        Services c = m.map(dto, Services.class);
        a.insert(c);
    }

    @GetMapping
    public List<ServicesDTO> Listar(){
        return a.list().stream().map(x->{
            ModelMapper m = new ModelMapper();
            return m.map(x, ServicesDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id){
        a.delete(id);
    }

    @GetMapping("/{id}")
    public ServicesDTO ListarId(@PathVariable("id") Integer id){
        ModelMapper m = new ModelMapper();
        ServicesDTO dto = m.map((a.listId(id)), ServicesDTO.class);
        return dto;
    }

    @PutMapping
    public void Update(@RequestBody ServicesDTO dto){
        ModelMapper m = new ModelMapper();
        Services c = m.map(dto, Services.class);
        a.insert(c);
    }
    @GetMapping("/{id}/perfiles")
    public List<PerfilDTO> listarPerfilesPorServicio(@PathVariable("id") Integer id) {
        return perfilService.findByServiceId(id).stream().map(perfil -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(perfil, PerfilDTO.class);
        }).collect(Collectors.toList());
    }
}
