package com.gestioncliente.gestionclientenew.repositories;

import com.gestioncliente.gestionclientenew.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    Long countByUserIdAndReadFalse(Long userId);
    List<Notification> findByUserIdAndReadFalse(Long userId);
}