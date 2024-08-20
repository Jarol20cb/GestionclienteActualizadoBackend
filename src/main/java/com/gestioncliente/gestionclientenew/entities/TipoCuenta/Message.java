package com.gestioncliente.gestionclientenew.entities.TipoCuenta;

import com.gestioncliente.gestionclientenew.entities.Users;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition="TEXT", nullable = true)
    private String fileData;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatus status;

    public Message() {
    }

    public Message(Long id, String username, String title, String fileData, LocalDateTime createdAt, MessageStatus status) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.fileData = fileData;
        this.createdAt = createdAt;
        this.status = status;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}