package com.metagen.marketservice.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metagen.marketservice.model.Subscription;
import com.metagen.marketservice.service.MarketDataFeedService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MarketWebSocketHandler extends TextWebSocketHandler {

    private final MarketDataFeedService marketDataFeedService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<WebSocketSession, Subscription> subscriptions = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("🔌 Connection established: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Map<String, Object> request = objectMapper.readValue(message.getPayload(), Map.class);
        String type = (String) request.get("type");

        if ("subscribe".equalsIgnoreCase(type)) {
            List<String> symbols = (List<String>) request.get("symbols");
            String timeframe = (String) request.get("timeframe");
            long intervalSeconds = parseTimeframeToSeconds(timeframe);

            Subscription sub = new Subscription(symbols, intervalSeconds);
            subscriptions.put(session, sub);
            session.sendMessage(new TextMessage("✅ Subscribed to " + symbols + " every " + intervalSeconds + "s"));

            scheduler.scheduleAtFixedRate(() -> sendUpdates(session, sub), 0, intervalSeconds, TimeUnit.SECONDS);
        } else {
            session.sendMessage(new TextMessage("❌ Unknown request type: " + type));
        }
    }

    private void sendUpdates(WebSocketSession session, Subscription sub) {
        if (!session.isOpen()) {
            subscriptions.remove(session);
            return;
        }

        for (String symbol : sub.getSymbols()) {
            try {
                Map<String, Object> data = marketDataFeedService.getLivePrice(symbol);
                String json = objectMapper.writeValueAsString(data);
                session.sendMessage(new TextMessage(json));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long parseTimeframeToSeconds(String timeframe) {
        try {
            if (timeframe.endsWith("s")) {
                return Long.parseLong(timeframe.replace("s", ""));
            } else if (timeframe.endsWith("m")) {
                return Long.parseLong(timeframe.replace("m", "")) * 60;
            }
        } catch (Exception e) {
            System.out.println("⚠️ Invalid timeframe format. Defaulting to 60s");
        }
        return 60;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        subscriptions.remove(session);
        System.out.println("❌ Connection closed: " + session.getId());
    }
}
