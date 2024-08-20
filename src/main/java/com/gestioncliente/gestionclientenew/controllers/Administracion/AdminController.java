package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.dtos.MessageDTO;
import com.gestioncliente.gestionclientenew.entities.*;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.Message;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.MessageStatus;
import com.gestioncliente.gestionclientenew.repositories.*;
import com.gestioncliente.gestionclientenew.serviceimplements.JwtUserDetailsService;
import com.gestioncliente.gestionclientenew.serviceimplements.PasswordService;
import com.gestioncliente.gestionclientenew.serviceinterfaces.IMessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private CustomerServiceRepository customerServiceRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}/enable")
    public ResponseEntity<?> enableUser(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }
        user.setEnabled(true);
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}/disable")
    public ResponseEntity<?> disableUser(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }
        user.setEnabled(false);
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    @Transactional
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }
        rolRepository.deleteByUserId(user.getId());
        List<Services> services = servicesRepository.findByUsername(user.getUsername());
        for (Services service : services) {
            List<Perfil> perfiles = perfilRepository.findByServiceId(service.getServiceId());
            for (Perfil perfil : perfiles) {
                List<CustomerService> customerServices = customerServiceRepository.findByUsername(perfil.getUsername());
                for (CustomerService customerService : customerServices) {
                    customerServiceRepository.delete(customerService);
                }
                perfilRepository.delete(perfil);
            }
            servicesRepository.delete(service);
        }
        List<Proveedor> proveedores = proveedorRepository.findByUsername(user.getUsername());
        for (Proveedor proveedor : proveedores) {
            proveedorRepository.delete(proveedor);
        }

        userRepo.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }

        user.setUsername(updatedUser.getUsername());
        user.setName(updatedUser.getName());
        user.setCompanyName(updatedUser.getCompanyName());
        user.setEnabled(updatedUser.getEnabled());

        if (!passwordService.matches(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(passwordService.encodePassword(updatedUser.getPassword()));
        }

        List<Role> currentRoles = user.getRoles();
        List<Role> newRoles = updatedUser.getRoles();

        currentRoles.removeIf(role -> newRoles.stream().noneMatch(newRole -> newRole.getRol().equals(role.getRol())));

        for (Role newRole : newRoles) {
            if (currentRoles.stream().noneMatch(existingRole -> existingRole.getRol().equals(newRole.getRol()))) {
                newRole.setUser(user);
                currentRoles.add(newRole);
            }
        }

        user.setRoles(currentRoles);
        userRepo.save(user);

        Users refreshedUser = userRepo.findById(id).orElse(null);

        return ResponseEntity.ok(refreshedUser);
    }

    @PostMapping("/notifications/send")
    public ResponseEntity<?> sendNotifications(@RequestBody NotificationRequest notificationRequest) {
        for (Long userId : notificationRequest.getUserIds()) {
            Users user = userRepo.findById(userId).orElse(null);
            if (user != null) {
                Notification notification = new Notification();
                notification.setMessage(notificationRequest.getMessage());
                notification.setUser(user);
                notification.setRead(false);
                notificationRepository.save(notification);
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("message", "Notificaciones enviadas");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/notifications/sendToAll")
    public ResponseEntity<?> sendNotificationsToAll(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            return ResponseEntity.status(400).body("El mensaje no puede estar vacío");
        }

        List<Users> users = userRepo.findAll();
        for (Users user : users) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(user);
            notification.setRead(false);
            notificationRepository.save(notification);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Notificaciones enviadas a todos los usuarios");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/notifications")
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        notifications.forEach(notification -> notification.getUser().getUsername());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/users/{id}/notifications")
    public ResponseEntity<?> getNotificationsByUserId(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }
        List<Notification> notifications = notificationRepository.findByUserId(id);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification == null) {
            return ResponseEntity.status(404).body("Notificación no existe");
        }
        notificationRepository.delete(notification);
        return ResponseEntity.ok("Notificación eliminada correctamente");
    }

    @DeleteMapping("/notifications")
    public ResponseEntity<?> deleteAllNotifications() {
        notificationRepository.deleteAll();
        return ResponseEntity.ok("Todas las notificaciones han sido eliminadas");
    }


    @GetMapping("/pagos-pendientes")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<MessageDTO> Listar(){
        return messageService.getAllMessages().stream().map(x->{
            ModelMapper m = new ModelMapper();
            return m.map(x, MessageDTO.class);
        }).collect(Collectors.toList());
    }

    @PutMapping("/pagos/{id}/aceptar")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> aceptarPago(@PathVariable Long id) {
        Message mensaje = messageService.getAllMessages().stream()
                .filter(msg -> msg.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (mensaje == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
        }

        Users usuario = userRepo.findByUsername(mensaje.getUsername());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        LocalDateTime now = LocalDateTime.now();

        if (usuario.getAccountType() == AccountType.FREE) {
            usuario.setSubscriptionStartDate(now);
            usuario.setSubscriptionEndDate(now.plusDays(30));
            usuario.setIsPremium(true);
            usuario.setAccountType(AccountType.PREMIUM);
            usuario.setLastPaymentDate(now);
        } else if (usuario.getAccountType() == AccountType.PREMIUM) {
            usuario.setSubscriptionEndDate(usuario.getSubscriptionEndDate().plusDays(30));
            usuario.setLastPaymentDate(now);
        }

        userRepo.save(usuario);

        Notification notification = new Notification();
        notification.setMessage("Su pago ha sido Aceptado. Gracias por confiar en nosotros, disfruta de tus beneficios Premium \uD83D\uDE0A");
        notification.setUser(usuario);
        notification.setRead(false);
        notificationRepository.save(notification);

        mensaje.setStatus(MessageStatus.ACCEPTED);
        messageService.saveMessage(mensaje);

        return ResponseEntity.ok("Pago aceptado y suscripción actualizada");
    }

    @PutMapping("/pagos/{id}/rechazar")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> rechazarPago(@PathVariable Long id) {
        Message mensaje = messageService.getAllMessages().stream()
                .filter(msg -> msg.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (mensaje == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
        }

        Users usuario = userRepo.findByUsername(mensaje.getUsername());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        if (usuario.getAccountType() == AccountType.FREE) {
            usuario.setLastPaymentDate(null);
            userRepo.save(usuario);
        }

        Notification notification = new Notification();
        notification.setMessage("Su pago ha sido rechazado. Por favor, intente nuevamente o contacte a soporte.");
        notification.setUser(usuario);
        notification.setRead(false);
        notificationRepository.save(notification);

        mensaje.setStatus(MessageStatus.REJECTED);
        messageService.saveMessage(mensaje);

        return ResponseEntity.ok("Pago rechazado y notificación enviada");
    }

}
