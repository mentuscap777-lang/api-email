package com.sebastian.email_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class EmailService {

    private final RestClient restClient;

    public EmailService(@Value("${worker.url}") String workerUrl) {
        this.restClient = RestClient.builder().baseUrl(workerUrl).build();
    }

    public ResponseEntity<String> enviar(EmailRequest request) {
        try {
            String respuesta = restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(String.class);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(respuesta);
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(e.getResponseBodyAsString());
        }
    }
}