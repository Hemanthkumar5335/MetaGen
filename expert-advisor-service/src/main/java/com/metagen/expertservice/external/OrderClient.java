package com.metagen.expertservice.external;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.metagen.expertservice.dto.OrderResponse;
import com.metagen.expertservice.dto.SignalRequest;
@Component
public class OrderClient {

    private final RestTemplate restTemplate;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Autowired
    public OrderClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderResponse sendOrder(SignalRequest signal, String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        HttpEntity<SignalRequest> request = new HttpEntity<>(signal, headers);

        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
                orderServiceUrl + "/api/orders/place",
                request,
                OrderResponse.class
        );

        return response.getBody();
    }
}
