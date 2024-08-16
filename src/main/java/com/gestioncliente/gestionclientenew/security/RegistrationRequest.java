package com.gestioncliente.gestionclientenew.security;

import com.gestioncliente.gestionclientenew.entities.TipoCuenta.AccountType;

import java.util.List;

public class RegistrationRequest extends JwtRequest {
    private List<String> roles;
    private String name;
    private String companyName;
    private AccountType accountType;  // Tipo de cuenta seleccionado por el usuario

    // Getters y Setters
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
