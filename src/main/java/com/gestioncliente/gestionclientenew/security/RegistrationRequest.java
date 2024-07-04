package com.gestioncliente.gestionclientenew.security;

import java.util.List;

public class RegistrationRequest extends JwtRequest {
    private List<String> roles;
    private String name; // Nuevo campo
    private String companyName; // Nuevo campo

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
}
