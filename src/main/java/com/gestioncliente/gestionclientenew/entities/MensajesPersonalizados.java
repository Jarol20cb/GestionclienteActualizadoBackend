package com.gestioncliente.gestionclientenew.entities;

import javax.persistence.*;

@Entity
@Table(name = "mensajespersonalizados")
public class MensajesPersonalizados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "username", nullable = false)
    private String username;

    public MensajesPersonalizados() {
    }

    public MensajesPersonalizados(int id, String titulo, String message, String username) {
        this.id = id;
        this.titulo = titulo;
        this.message = message;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
