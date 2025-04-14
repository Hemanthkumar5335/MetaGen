package com.metagen.orderservice.controller;

import com.metagen.orderservice.dto.OrderRequest;
import com.metagen.orderservice.dto.OrderResponse;
import com.metagen.orderservice.service.OrderService;
import com.metagen.orderservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody OrderRequest req,
            @RequestHeader("Authorization") String auth) {

        String token = auth.substring(7);
        String user = jwtUtil.extractUsername(token);
        return ResponseEntity.ok(service.placeOrder(user, req, auth));
    }
}
