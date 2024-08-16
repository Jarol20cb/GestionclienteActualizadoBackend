package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.dtos.MessageDTO;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.serviceinterfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUsersRepository userRepository;

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

    // Listar solo los registros del usuario autenticado
    @GetMapping
    public ResponseEntity<List<MessageDTO>> getUserMessages() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users currentUser = userRepository.findByUsername(username);

        List<Message> messages = messageService.getMessagesByUser(currentUser);
        List<MessageDTO> messageDTOs = messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(messageDTOs);
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

    @PostMapping("/uploadReceipt")
    public ResponseEntity<Map<String, String>> uploadReceipt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "El archivo está vacío"));
            }

            // Obtener el usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            Users currentUser = userRepository.findByUsername(username);

            if (currentUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Usuario no encontrado"));
            }

            // Crear un nuevo mensaje y asignar los datos
            Message message = new Message();
            message.setUser(currentUser);
            message.setTitle(title);
            message.setCreatedAt(LocalDateTime.now());
            message.setFileData(file.getBytes());

            // Guardar el mensaje
            messageService.saveMessage(message);

            return ResponseEntity.ok(Map.of("message", "Comprobante subido exitosamente."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al procesar el archivo"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al subir el comprobante."));
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