package com.gestioncliente.gestionclientenew.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class UserWithoutPasswordDTO {
    private Long id;
    private String username;
    private Boolean enabled;
    private String name;
    private String companyName;
    private String accountType;
    private String accountStatus;  // Nuevo campo agregado
    private LocalDateTime registrationDate;
    private LocalDateTime premiumStartDate;
    private LocalDateTime lastPaymentDate;
    private Long timeUntilNextPayment;
    private List<String> roles;

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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getPremiumStartDate() {
        return premiumStartDate;
    }

    public void setPremiumStartDate(LocalDateTime premiumStartDate) {
        this.premiumStartDate = premiumStartDate;
    }

    public LocalDateTime getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Long getTimeUntilNextPayment() {
        return timeUntilNextPayment;
    }

    public void setTimeUntilNextPayment(Long timeUntilNextPayment) {
        this.timeUntilNextPayment = timeUntilNextPayment;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
