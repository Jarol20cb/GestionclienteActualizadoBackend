package com.gestioncliente.gestionclientenew.controllers;
import com.gestioncliente.gestionclientenew.dtos.UserDTO;
import com.gestioncliente.gestionclientenew.entities.Users;
import com.gestioncliente.gestionclientenew.repositories.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUsersRepository userRepo;

    @GetMapping("/details")
    public UserDTO getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Users user = userRepo.findByUsername(username);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setName(user.getName());
        userDTO.setCompanyName(user.getCompanyName());
        userDTO.setAccountType(user.getAccountType());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastPaymentDate(user.getLastPaymentDate());
        userDTO.setSubscriptionStartDate(user.getSubscriptionStartDate());
        userDTO.setSubscriptionEndDate(user.getSubscriptionEndDate());
        List<String> roles = user.getRoles().stream().map(role -> role.getRol()).collect(Collectors.toList());
        userDTO.setRoles(roles);

        return userDTO;
    }

}