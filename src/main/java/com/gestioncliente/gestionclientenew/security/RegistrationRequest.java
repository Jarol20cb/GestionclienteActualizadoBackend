package com.gestioncliente.gestionclientenew.security;

import java.util.List;

public class RegistrationRequest extends JwtRequest{
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
