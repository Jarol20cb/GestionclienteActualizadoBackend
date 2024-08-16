package com.gestioncliente.gestionclientenew.dtos;

import java.time.LocalDateTime;

public class MessageDTO {

    private Long id;
    private Long userId;
    private String title;
    private byte[] fileData;  // Datos binarios del archivo (imagen)
    private LocalDateTime createdAt;

    // Constructor vacío
    public MessageDTO() {}

    // Constructor con parámetros
    public MessageDTO(Long id, Long userId, String title, byte[] fileData, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.fileData = fileData;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
