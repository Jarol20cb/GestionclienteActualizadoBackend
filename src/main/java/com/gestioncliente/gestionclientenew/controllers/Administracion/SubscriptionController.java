package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private IUsersRepository userRepo;

    // Endpoint para convertir una cuenta Free a Premium
    @PutMapping("/{id}/upgrade")
    public ResponseEntity<String> upgradeToPremium(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }

        if (!user.getIsPremium()) {
            user.setAccountType(AccountType.PREMIUM);
            user.setIsPremium(true);
            user.setSubscriptionStartDate(LocalDateTime.now());
            user.setSubscriptionEndDate(LocalDateTime.now().plusDays(30));  // Premium por 30 días
            userRepo.save(user);
            return ResponseEntity.ok("Cuenta actualizada a Premium.");
        } else {
            return ResponseEntity.badRequest().body("El usuario ya es Premium.");
        }
    }

    // Endpoint para renovar una cuenta Premium
    @PutMapping("/{id}/renew")
    public ResponseEntity<String> renewSubscription(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }

        if (user.getIsPremium()) {
            LocalDateTime newEndDate = user.getSubscriptionEndDate().plusDays(30);  // Extender por 30 días
            user.setSubscriptionEndDate(newEndDate);
            userRepo.save(user);
            return ResponseEntity.ok("Suscripción Premium renovada.");
        } else {
            return ResponseEntity.badRequest().body("El usuario no es Premium.");
        }
    }

    // Endpoint para verificar el estado de la suscripción
    @GetMapping("/{id}/status")
    public ResponseEntity<String> checkSubscriptionStatus(@PathVariable Long id) {
        Users user = userRepo.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(user.getSubscriptionEndDate())) {
            return ResponseEntity.ok("Suscripción activa hasta " + user.getSubscriptionEndDate());
        } else {
            return ResponseEntity.ok("Suscripción vencida.");
        }
    }
}