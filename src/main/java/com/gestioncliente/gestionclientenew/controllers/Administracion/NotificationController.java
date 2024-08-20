package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.entities.Notification;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/current/unread-count")
    public ResponseEntity<Long> getUnreadNotificationsCountForCurrentUser(Principal principal) {
        Users user = userRepo.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        Long unreadCount = notificationRepository.countByUserIdAndReadFalse(user.getId());
        return ResponseEntity.ok(unreadCount);
    }

    @PostMapping("/user/current/mark-all-read")
    public ResponseEntity<Void> markAllNotificationsAsRead(Principal principal) {
        Users user = userRepo.findByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalse(user.getId());

        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        for (Notification notification : notifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok().build();
    }

}

