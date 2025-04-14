package com.metagen.marketservice.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class MarketDataFeedService {

    public Map<String, Object> getLivePrice(String symbol) {
        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        data.put("bid", random(1.0, 1.5));
        data.put("ask", random(1.5, 2.0));
        data.put("timestamp", Instant.now().toString());
        return data;
    }

    private double random(double min, double max) {
        return Math.round((min + new Random().nextDouble() * (max - min)) * 100000.0) / 100000.0;
    }
}
