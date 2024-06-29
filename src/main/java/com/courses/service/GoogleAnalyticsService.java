package com.courses.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class GoogleAnalyticsService {


    String clientId = UUID.randomUUID().toString();

    private final String measurementId = "G-9GXQQXJK58"; // Substitua pelo seu Measurement ID
    private final String apiSecret = "C5yreDDyQZejewJPjaPl9Q"; // Substitua pelo seu API Secret

    public void sendEvent(String eventName,String category, String action, String label, int value) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.google-analytics.com/mp/collect?measurement_id=" + measurementId +
                "&api_secret=" + apiSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        StringBuilder eventJson = new StringBuilder();
        eventJson.append("{");
        eventJson.append("\"client_id\": \" " + clientId + "\",");
        eventJson.append("\"events\": [");
        eventJson.append("{");
        eventJson.append("\"name\": \"" + eventName + "\",");
        eventJson.append("\"params\": {");
        eventJson.append("\"category\": \"" + category + "\",");
        eventJson.append("\"action\": \"" + action + "\",");
        eventJson.append("\"label\": \"" + label + "\",");
        eventJson.append("\"value\": " + value);
        eventJson.append("}");
        eventJson.append("}");
        eventJson.append("]");
        eventJson.append("}");

        HttpEntity<String> entity = new HttpEntity<>(eventJson.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}