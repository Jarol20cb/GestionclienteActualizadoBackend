package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.dtos.MessageDTO;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public void registrar(@RequestBody MessageDTO dto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ModelMapper m = new ModelMapper();
        Message fT = m.map(dto, Message.class);
        fT.setUser(userRepository.findByUsername(username));
        fT.setCreatedAt(LocalDateTime.now());
        messageService.saveMessage(fT);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<MessageDTO> Listar(){
        return messageService.getAllMessages().stream().map(x->{
            ModelMapper m = new ModelMapper();
            return m.map(x, MessageDTO.class);
        }).collect(Collectors.toList());
    }


    // Listar todos los registros (solo para admin)
    @GetMapping("/all")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users currentUser = userRepository.findByUsername(username);

        if (currentUser.getRoles().stream().anyMatch(role -> role.getRol().equals("ADMIN"))) {
            List<Message> messages = messageService.getAllMessages();
            List<MessageDTO> messageDTOs = messages.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(messageDTOs);
        } else {
            return ResponseEntity.status(403).body(null);
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

    // Convertir Message a MessageDTO
    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getUser().getId(),
                message.getTitle(),
                message.getFileData(),
                message.getCreatedAt()
        );
    }
}