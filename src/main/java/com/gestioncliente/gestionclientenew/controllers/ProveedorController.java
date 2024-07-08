package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.dtos.ProveedorDTO;
import com.gestioncliente.gestionclientenew.entities.Proveedor;
import com.gestioncliente.gestionclientenew.serviceinterfaces.ProveedorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @PostMapping
    public void registrar(@RequestBody ProveedorDTO dto) {
        ModelMapper m = new ModelMapper();
        Proveedor p = m.map(dto, Proveedor.class);
        proveedorService.insert(p);
    }

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, ProveedorDTO.class);
        }).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        proveedorService.delete(id);
    }

    @GetMapping("/{id}")
    public ProveedorDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m = new ModelMapper();
        ProveedorDTO dto = m.map(proveedorService.listId(id), ProveedorDTO.class);
        return dto;
    }

    @PutMapping
    public void actualizar(@RequestBody ProveedorDTO dto) {
        ModelMapper m = new ModelMapper();
        Proveedor p = m.map(dto, Proveedor.class);
        proveedorService.insert(p);
    }
}
