package com.gestioncliente.gestionclientenew.controllers.Administracion;

import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUsersRepository userRepo;

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
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }
}
