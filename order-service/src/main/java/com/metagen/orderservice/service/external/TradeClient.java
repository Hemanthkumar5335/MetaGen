package com.metagen.orderservice.service.external;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.metagen.orderservice.entity.Order;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TradeClient {

    private final RestTemplate restTemplate;

    @Value("${trade.service.url}")
    private String tradeServiceUrl;

    public void sendTrade(Order order) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("order_id", String.valueOf(order.getId()));
        payload.put("symbol", order.getSymbol());
        payload.put("side", order.getSide());
        payload.put("volume", order.getVolume());
        payload.put("execution_price", order.getExecutionPrice());
        payload.put("status", "closed"); // assuming all are closed
        payload.put("timestamp", order.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);
        restTemplate.postForEntity(tradeServiceUrl + "/api/orders", entity, Void.class);
    }
}
