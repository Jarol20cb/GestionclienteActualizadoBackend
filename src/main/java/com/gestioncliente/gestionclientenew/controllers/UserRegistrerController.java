package com.gestioncliente.gestionclientenew.controllers;

import com.gestioncliente.gestionclientenew.entities.Role;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IRolRepository;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import com.gestioncliente.gestionclientenew.security.RegistrationRequest;
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
//Controlador para insertar registro de usuario con sus roles, requiere del archivo RegistrationRequest
//y una pequeña modificadión en la carpeta WebSecurityConfig

//y para registrar en el Postman es de esta manera:
//{
//    "username": "jose",
//    "password": "jose",
//    "roles": ["CARPENTER"]
//}
@RestController
@CrossOrigin
public class UserRegistrerController {

    @Autowired
    private IUsersRepository userRepo;

    @Autowired
    private IRolRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            // Comprobar si el nombre de usuario ya existe
            if (userRepo.findByUsername(registrationRequest.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "El nombre de usuario ya existe"));
            }

            // Crear nuevo usuario
            Users newUser = new Users();
            newUser.setUsername(registrationRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            newUser.setEnabled(true);
            newUser.setName(registrationRequest.getName());
            newUser.setCompanyName(registrationRequest.getCompanyName());

            // Establecer el tipo de cuenta y las fechas correspondientes
            AccountType accountType = registrationRequest.getAccountType();
            newUser.setAccountType(accountType);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setSubscriptionStartDate(LocalDateTime.now());

            if (accountType == AccountType.FREE) {
                newUser.setSubscriptionEndDate(LocalDateTime.now().plusDays(15));  // Free por 15 días
                newUser.setIsPremium(false);
            } else if (accountType == AccountType.PREMIUM) {
                newUser.setSubscriptionEndDate(LocalDateTime.now().plusDays(30));  // Premium por 30 días
                newUser.setIsPremium(true);
            }

            // Guardar usuario en la base de datos
            userRepo.save(newUser);

            // Crear y guardar roles para el usuario
            for (String roleName : registrationRequest.getRoles()) {
                Role newRole = new Role();
                newRole.setRol(roleName);
                newRole.setUser(newUser);
                roleRepo.save(newRole);
            }

            return ResponseEntity.ok(Map.of("message", "Usuario registrado con éxito :)"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Hubo un error en el registro"));
        }
    }

}
