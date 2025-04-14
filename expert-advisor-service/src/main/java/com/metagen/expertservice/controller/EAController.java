package com.metagen.expertservice.controller;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metagen.expertservice.dto.OrderResponse;
import com.metagen.expertservice.dto.SignalRequest;
import com.metagen.expertservice.entity.EALog;
import com.metagen.expertservice.external.OrderClient;
import com.metagen.expertservice.service.EAService;
import com.metagen.expertservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ea")
@RequiredArgsConstructor
public class EAController {

    private final EAService service;
    private final JwtUtil jwtUtil;
    private final OrderClient orderClient; // Injecting the OrderClient

    // Endpoint to get logs based on the user extracted from JWT
    @GetMapping("/logs")
    public ResponseEntity<List<EALog>> getLogs(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);  // Extract user from token
        return ResponseEntity.ok(service.getLogsByUser(username));  // Fetch logs from DB
    }

    // Endpoint to post an order and log it
    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody SignalRequest signalRequest,
                                        @RequestHeader("Authorization") String authHeader) {
        // Extract user info from JWT token
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token); 

        // Send the order to the order service using OrderClient
        OrderResponse orderResponse = orderClient.sendOrder(signalRequest, authHeader);

        // Check if the order was placed successfully
        if (orderResponse != null && "success".equals(orderResponse.getStatus())) {
            // Log the action after successful order placement
            service.log(username, "Placed Order", signalRequest.getSymbol());
            return ResponseEntity.ok(Map.of("status", "success", "orderResponse", orderResponse));
        } else {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Order placement failed"));
        }
    }
}
