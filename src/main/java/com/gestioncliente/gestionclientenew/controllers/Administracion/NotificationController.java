package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.entities.Notification;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private IUsersRepository userRepo;

    @GetMapping("/user/current")
    public ResponseEntity<List<Notification>> getNotificationsForCurrentUser(Principal principal) {
        Users user = userRepo.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        List<Notification> notifications = notificationRepository.findByUserId(user.getId());
        return ResponseEntity.ok(notifications);
    }
}