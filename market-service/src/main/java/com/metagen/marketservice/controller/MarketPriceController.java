package com.metagen.marketservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metagen.marketservice.service.MarketDataFeedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class MarketPriceController {

    private final MarketDataFeedService feedService;

    @GetMapping("/price")
    public Map<String, Object> getPrice(@RequestParam String symbol) {
        return feedService.getLivePrice(symbol);
    }
}
