package com.courses.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class GoogleAnalyticsService {

    String measurementId = "G-9GXQQXJK58";
    String apiSecret = "C5yreDDyQZejewJPjaPl9Q";

    public void sendEvent(String eventName, String category, String action, String label, int value) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://www.google-analytics.com/mp/collect?measurement_id=" + measurementId +
                "&api_secret=" + apiSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(buildEventJson(eventName, category, action, label, value), headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    private String buildEventJson(String eventName, String category, String action, String label, int value) {
        StringBuilder eventJson = new StringBuilder();
        eventJson.append("{");
        eventJson.append("\"client_id\": \"" + UUID.randomUUID() + "\",");
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

        return eventJson.toString();
    }
}