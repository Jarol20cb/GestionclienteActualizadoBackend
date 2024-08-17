package com.gestioncliente.gestionclientenew.dtos;

import java.time.LocalDateTime;

public class MessageDTO {

    private Long id;
    private Long userId;
    private String title;
    private String fileData;
    private LocalDateTime createdAt;

    // Constructor vacío
    public MessageDTO() {}

    // Constructor con parámetros

    public MessageDTO(Long id, Long userId, String title, String fileData, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.fileData = fileData;
        this.createdAt = createdAt;
    }

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
}
