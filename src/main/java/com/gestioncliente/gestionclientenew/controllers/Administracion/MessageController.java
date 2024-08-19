package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.dtos.MessageDTO;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.MessageStatus;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUsersRepository userRepository;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<MessageDTO> listar() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return messageService.findByUsername(username).stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, MessageDTO.class);
        }).collect(Collectors.toList());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody MessageDTO dto) {
        ModelMapper m = new ModelMapper();
        Message p = m.map(dto, Message.class);

        // Establecer el estado como PENDIENTE
        p.setStatus(MessageStatus.PENDING);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        p.setUsername(username);

        // Guardar el mensaje
        messageService.saveMessage(p);

        // Obtener el usuario actual
        Users usuario = userRepository.findByUsername(username);
        if (usuario.getAccountType() == AccountType.FREE) {
            LocalDateTime now = LocalDateTime.now();
            usuario.setLastPaymentDate(now);
            usuario.setIsPremium(true);
            usuario.setAccountType(AccountType.PREMIUM);
            usuario.setSubscriptionEndDate(now.plusHours(24));
            userRepository.save(usuario);
        }
    }



    // Eliminar un registro (solo para admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users currentUser = userRepository.findByUsername(username);

        if (currentUser.getRoles().stream().anyMatch(role -> role.getRol().equals("ADMIN"))) {
            messageService.deleteMessage(id);
            return ResponseEntity.ok("Mensaje eliminado con éxito.");
        } else {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar mensajes.");
        }
    }


}