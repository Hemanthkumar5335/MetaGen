package com.metagen.orderservice.service.external;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.metagen.orderservice.dto.RiskCheckRequest;

@Component
public class RiskClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isRiskApproved(RiskCheckRequest req, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<RiskCheckRequest> entity = new HttpEntity<>(req, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "http://localhost:8083/api/risk/check", // ✅ Change if using different port
                HttpMethod.POST,
                entity,
                Map.class
        );

        return "ALLOWED".equalsIgnoreCase((String) response.getBody().get("status"));
    }
}
