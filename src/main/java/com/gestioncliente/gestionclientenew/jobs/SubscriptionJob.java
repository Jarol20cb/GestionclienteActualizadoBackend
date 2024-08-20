package com.gestioncliente.gestionclientenew.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;

@Component
public class SubscriptionJob {

    @Autowired
    private IUsersRepository userRepo;

    @Scheduled(cron = "0 0 * * * *")
    public void checkSubscriptions() {
        System.out.println("Inicio de la revisión de suscripciones: " + LocalDateTime.now());

        List<Users> users = userRepo.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Users user : users) {
            if (user.getAccountType() == AccountType.PREMIUM && user.getLastPaymentDate() == null) {
                if (user.getCreatedAt().plusHours(24).isBefore(now)) {
                    user.setAccountType(AccountType.FREE);
                    user.setIsPremium(false);
                    user.setSubscriptionStartDate(now);
                    user.setSubscriptionEndDate(now.plusDays(15));
                    userRepo.save(user);
                    System.out.println("Usuario " + user.getUsername() + " cambiado a FREE debido a falta de pago.");
                }
            }

            else if (user.getAccountType() == AccountType.PREMIUM && user.getIsPremium() && user.getLastPaymentDate() == null) {
                if (user.getSubscriptionStartDate().plusHours(24).isBefore(now) && user.getCreatedAt().plusDays(15).isAfter(now)) {
                    user.setAccountType(AccountType.FREE);
                    user.setIsPremium(false);

                    long daysRemaining = ChronoUnit.DAYS.between(now, user.getSubscriptionEndDate());
                    user.setSubscriptionEndDate(now.plusDays(daysRemaining));

                    userRepo.save(user);
                    System.out.println("Usuario " + user.getUsername() + " restaurado a FREE debido a falta de confirmación de pago.");
                }
            }

        }

        System.out.println("Fin de la revisión de suscripciones: " + LocalDateTime.now());
    }
}
