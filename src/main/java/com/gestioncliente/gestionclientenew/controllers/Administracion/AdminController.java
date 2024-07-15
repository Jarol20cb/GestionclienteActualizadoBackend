package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.entities.*;
import com.gestioncliente.gestionclientenew.repositories.*;
import com.gestioncliente.gestionclientenew.serviceimplements.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

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

        // Eliminar registros asociados en orden adecuado
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

        // Finalmente, eliminar el usuario
        userRepo.delete(user);

        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Users user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuario no existe");
        }

        // Actualizar los campos del usuario
        user.setUsername(updatedUser.getUsername());
        user.setName(updatedUser.getName());
        user.setCompanyName(updatedUser.getCompanyName());
        user.setEnabled(updatedUser.getEnabled());

        // Encriptar la contraseña si es diferente de la actual
        if (!passwordService.matches(updatedUser.getPassword(), user.getPassword())) {
            user.setPassword(passwordService.encodePassword(updatedUser.getPassword()));
        }

        // Obtener roles actuales del usuario
        List<Role> currentRoles = user.getRoles();
        List<Role> newRoles = updatedUser.getRoles();

        // Mantener solo los roles actualizados
        currentRoles.removeIf(role -> newRoles.stream().noneMatch(newRole -> newRole.getRol().equals(role.getRol())));

        // Añadir nuevos roles si no existen
        for (Role newRole : newRoles) {
            if (currentRoles.stream().noneMatch(existingRole -> existingRole.getRol().equals(newRole.getRol()))) {
                newRole.setUser(user);
                currentRoles.add(newRole);
            }
        }

        user.setRoles(currentRoles);
        userRepo.save(user);

        // Refrescar la entidad para obtener los cambios más recientes
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



}

