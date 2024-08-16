package com.gestioncliente.gestionclientenew.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String username;  // Nombre de usuario único

    @Column(length = 200, nullable = false)
    private String password;  // Contraseña cifrada del usuario

    private Boolean enabled;  // Estado de habilitación de la cuenta (activo/inactivo)

    @Column(length = 50, nullable = false)
    private String name;  // Nombre completo del usuario

    @Column(length = 100, nullable = false)
    private String companyName;  // Nombre de la empresa asociada al usuario

    @Column(nullable = false)
    private LocalDateTime createdAt;  // Fecha y hora de creación de la cuenta

    @Column(nullable = false)
    private LocalDateTime subscriptionStartDate;  // Fecha de inicio de la suscripción (free o premium)

    @Column(nullable = false)
    private LocalDateTime subscriptionEndDate;  // Fecha de expiración de la suscripción

    @Column(nullable = true)
    private LocalDateTime lastPaymentDate;  // Fecha del último pago realizado (solo para usuarios premium)

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private AccountType accountType;  // Tipo de cuenta: "FREE" o "PREMIUM"

    @Column(nullable = false)
    private Boolean isPremium;  // Indica si la cuenta es premium (true/false)

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles;  // Lista de roles asociados al usuario (e.g., ADMIN, USER)

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Notification> notifications;  // Notificaciones asociadas al usuario

    // Constructor vacío
    public Users() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    public void setSubscriptionStartDate(LocalDateTime subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    public LocalDateTime getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(LocalDateTime subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public LocalDateTime getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Boolean getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
        for (Role role : roles) {
            role.setUser(this);
        }
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        for (Notification notification : notifications) {
            notification.setUser(this);
        }
    }
}
