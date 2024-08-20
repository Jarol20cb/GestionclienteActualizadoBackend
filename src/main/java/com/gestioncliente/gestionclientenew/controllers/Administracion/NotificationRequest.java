package com.gestioncliente.gestionclientenew.controllers.Administracion;

import java.util.List;

class NotificationRequest {
    private String message;
    private List<Long> userIds;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}