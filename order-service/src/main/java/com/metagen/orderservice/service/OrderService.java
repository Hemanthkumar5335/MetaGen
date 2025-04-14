package com.metagen.orderservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.metagen.orderservice.dto.OrderRequest;
import com.metagen.orderservice.dto.OrderResponse;
import com.metagen.orderservice.dto.RiskCheckRequest;
import com.metagen.orderservice.entity.Order;
import com.metagen.orderservice.repository.OrderRepository;
import com.metagen.orderservice.service.external.RiskClient;
@Service
public class OrderService {

    private final OrderRepository repo;
    private final RiskClient riskClient;

    public OrderService(OrderRepository repo, RiskClient riskClient) {
        this.repo = repo;
        this.riskClient = riskClient;
    }

    public OrderResponse placeOrder(String username, OrderRequest req, String authHeader) {
        RiskCheckRequest riskReq = new RiskCheckRequest(req.getSymbol(), req.getVolume(), 100, 5000);
        boolean allowed = riskClient.isRiskApproved(riskReq, authHeader);

        if (!allowed) throw new RuntimeException("❌ Risk Check Failed");

        Order order = Order.builder()
                .user(username)
                .symbol(req.getSymbol())
                .volume(req.getVolume())
                .side(req.getSide())
                .orderType(req.getOrderType())
                .takeProfit(req.getTakeProfit())
                .stopLoss(req.getStopLoss())
                .status("PENDING")
                .executionPrice(null)
                .timestamp(LocalDateTime.now())
                .build();

        repo.save(order);
        return new OrderResponse(order.getId(), username, order.getStatus());
    }
}
