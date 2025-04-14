package com.metagen.orderservice.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metagen.orderservice.entity.Order;
import com.metagen.orderservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderWebSocketHandler extends TextWebSocketHandler {

    private final JwtUtil jwtUtil;

    // ✅ Register JavaTimeModule to handle LocalDateTime serialization
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final Map<WebSocketSession, String> sessionUsers = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String uri = session.getUri().toString();
        String token = null;

        if (uri.contains("token=")) {
            token = uri.substring(uri.indexOf("token=") + 6);
        }

        if (token != null) {
            try {
                String username = jwtUtil.extractUsername(token);
                sessionUsers.put(session, username);
                System.out.println("✅ WebSocket Connected for user: " + username);
            } catch (Exception e) {
                System.out.println("❌ Invalid JWT token in WebSocket");
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Missing token in WebSocket connection");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionUsers.remove(session);
    }

    public void broadcastOrder(Order order) {
        if (!"EXECUTED".equalsIgnoreCase(order.getStatus())) return;

        System.out.println("📣 Broadcasting: Order " + order.getId() + " for user: " + order.getUser());

        Map<String, Object> payload = Map.of(
                "orderId", order.getId(),
                "user", order.getUser(),
                "status", order.getStatus(),
                "executionPrice", order.getExecutionPrice(),
                "timestamp", order.getTimestamp()
        );

        sessionUsers.forEach((session, user) -> {
            System.out.println("👤 Active WebSocket user: " + user);

            if (user.equals(order.getUser()) && session.isOpen()) {
                try {
                    String message = objectMapper.writeValueAsString(payload);
                    session.sendMessage(new TextMessage(message));
                    System.out.println("✅ Message sent to: " + user);
                } catch (IOException e) {
                    System.out.println("❌ Failed to send message to: " + user);
                    e.printStackTrace();
                }
            }
        });
    }
}
