package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.entities.Role;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IRolRepository;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.security.RegistrationRequest;
import com.gestioncliente.gestionclientenew.jobs.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@CrossOrigin
public class UserRegistrerController {

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private IRolRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TelegramService telegramService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            if (userRepo.findByUsername(registrationRequest.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "El nombre de usuario ya existe"));
            }
            Users newUser = new Users();
            newUser.setUsername(registrationRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            newUser.setEnabled(true);
            newUser.setName(registrationRequest.getName());
            newUser.setCompanyName(registrationRequest.getCompanyName());
            AccountType accountType = registrationRequest.getAccountType();
            newUser.setAccountType(accountType);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setSubscriptionStartDate(LocalDateTime.now());
            newUser.setNumber(registrationRequest.getNumber());

            String telegramMessage = "";

            if (accountType == AccountType.FREE) {
                newUser.setSubscriptionEndDate(LocalDateTime.now().plusDays(15));
                newUser.setIsPremium(false);

                telegramMessage = "Nuevo usuario registrado: \n"
                        + "Nombre: " + newUser.getName() + "\n"
                        + "Nombre de usuario: " + newUser.getUsername() + "\n"
                        + "Empresa: " + newUser.getCompanyName() + "\n"
                        + "Contacto: " + newUser.getNumber() + "\n"
                        + "Tipo de cuenta: FREE\n";

            } else if (accountType == AccountType.PREMIUM) {
                newUser.setSubscriptionEndDate(LocalDateTime.now().plusDays(1));
                newUser.setIsPremium(true);

                telegramMessage = "Atención, un usuario se ha registrado como PREMIUM: \n"
                        + "Nombre: " + newUser.getName() + "\n"
                        + "Nombre de usuario: " + newUser.getUsername() + "\n"
                        + "Empresa: " + newUser.getCompanyName() + "\n"
                        + "Contacto: " + newUser.getNumber() + "\n"
                        + "Revisa la administración para confirmar sus pagos.";
            }

            userRepo.save(newUser);

            for (String roleName : registrationRequest.getRoles()) {
                Role newRole = new Role();
                newRole.setRol(roleName);
                newRole.setUser(newUser);
                roleRepo.save(newRole);
            }

            telegramService.sendTelegramMessage(telegramMessage);

            return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito :)"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Hubo un error en el registro"));
        }
    }

}
