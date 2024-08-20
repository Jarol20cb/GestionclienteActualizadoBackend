package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.dtos.MessageDTO;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.MessageStatus;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.jobs.TelegramService;
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

    @Autowired
    private TelegramService telegramService;

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
            // Aquí marcamos que el usuario ha solicitado ser Premium
            usuario.setIsPremium(true);
            usuario.setAccountType(AccountType.PREMIUM);
            usuario.setSubscriptionEndDate(LocalDateTime.now().plusHours(24));
            // Guardar sin establecer la fecha de pago aún
            userRepository.save(usuario);
        }

        // Enviar una notificación de Telegram
        telegramService.sendTelegramMessage(
                "El usuario " + username + " ha enviado un voucher y su cuenta ha sido actualizada a PREMIUM."
        );
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