package com.gestioncliente.gestionclientenew.jobs;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class TelegramService {

    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot7270700713:AAGbP1ieEBe4xPcdrUQjJO47hNqIQwFbQFY/sendMessage";

    public void sendTelegramMessage(String message) {
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(TELEGRAM_API_URL)
                .queryParam("chat_id", "2096781666")
                .queryParam("text", message)
                .toUriString();

        try {
            String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
            restTemplate.getForObject(decodedUrl, String.class);
            System.out.println("Mensaje de Telegram enviado.");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error al decodificar URL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al enviar mensaje de Telegram: " + e.getMessage());
        }
    }
}