package com.metagen.orderservice.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metagen.orderservice.dto.TradeDTO;
import com.metagen.orderservice.entity.Order;
import com.metagen.orderservice.handler.OrderWebSocketHandler;
import com.metagen.orderservice.repository.OrderRepository;
import com.metagen.orderservice.service.external.MarketClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExecutionEngine {

    private final OrderRepository repo;
    private final MarketClient marketClient;
    private final OrderWebSocketHandler wsHandler;
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 5000)
    public void executePendingOrders() {
        List<Order> pending = repo.findByStatus("PENDING");

        for (Order order : pending) {
            double price = marketClient.getExecutionPrice(order.getSymbol(), order.getSide());

            order.setExecutionPrice(price);
            order.setStatus("EXECUTED");

            repo.save(order);
            wsHandler.broadcastOrder(order);

            // ✅ Send to trade-service
            TradeDTO tradeDTO = new TradeDTO();
            tradeDTO.setOrder_id(order.getId().toString());
            tradeDTO.setUser(order.getUser());
            tradeDTO.setSymbol(order.getSymbol());
            tradeDTO.setSide(order.getSide());
            tradeDTO.setVolume(order.getVolume());
            tradeDTO.setExecution_price(order.getExecutionPrice());
            tradeDTO.setStatus("closed");
            tradeDTO.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));

            try {
                restTemplate.postForObject("http://localhost:8085/api/orders", tradeDTO, Void.class);
                System.out.println("✅ Trade sent to trade-service for user: " + tradeDTO.getUser());
            } catch (Exception e) {
                System.out.println("❌ Failed to send trade to trade-service");
                e.printStackTrace();
            }
        }
    }
}
