package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.SocioDTO;
import com.gestioncliente.gestionclientenew.dtos.referenciasociodto.CustomerServiceWithoutSocioDTO;
import com.gestioncliente.gestionclientenew.dtos.referenciasociodto.SocioCustomerServiceDTO;
import com.gestioncliente.gestionclientenew.entities.Socio;
import com.gestioncliente.gestionclientenew.serviceinterfaces.SocioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @PostMapping
    public void registrar(@RequestBody SocioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Socio socio = modelMapper.map(dto, Socio.class);
        socioService.insert(socio);
    }

    @GetMapping
    public List<SocioDTO> listar() {
        return socioService.list().stream().map(socio -> {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(socio, SocioDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        socioService.delete(id);
    }

    @GetMapping("/{id}")
    public SocioDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper modelMapper = new ModelMapper();
        SocioDTO dto = modelMapper.map(socioService.listId(id), SocioDTO.class);
        return dto;
    }

    @PutMapping
    public void update(@RequestBody SocioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Socio socio = modelMapper.map(dto, Socio.class);
        socioService.insert(socio);
    }

    @GetMapping("/{id}/customerservices")
    public SocioCustomerServiceDTO listarCustomerServicesPorSocio(@PathVariable("id") Integer id) {
        Socio socio = socioService.listId(id);
        if (socio != null) {
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
        return null; // Retorna null si el socio no existe
    }
}
