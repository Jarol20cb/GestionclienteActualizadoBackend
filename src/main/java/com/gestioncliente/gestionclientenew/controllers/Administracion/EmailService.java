package com.gestioncliente.gestionclientenew.controllers.Administracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        try {
            String subject = "Recuperación de Contraseña";
            String text = "Para restablecer tu contraseña, haz clic en el siguiente enlace: " +
                    "http://localhost:4200/reset-password?token=" + token;

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("Correo enviado a: " + to);

        } catch (Exception e) {
            System.out.println("Error enviando el correo: " + e.getMessage());
        }
    }
}
