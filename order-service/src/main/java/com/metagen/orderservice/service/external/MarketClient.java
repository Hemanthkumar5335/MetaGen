package com.metagen.orderservice.service.external;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class MarketClient {

    private final RestTemplate restTemplate;

    public double getExecutionPrice(String symbol, String side) {
        String url = "http://localhost:8082/api/market/price?symbol=" + symbol;
        Map<?, ?> response = restTemplate.getForObject(url, Map.class);

        return "buy".equalsIgnoreCase(side)
                ? Double.parseDouble(response.get("ask").toString())
                : Double.parseDouble(response.get("bid").toString());
    }
}
