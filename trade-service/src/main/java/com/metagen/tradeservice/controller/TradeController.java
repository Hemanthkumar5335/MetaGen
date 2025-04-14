package com.metagen.tradeservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metagen.tradeservice.dto.TradeDTO;
import com.metagen.tradeservice.service.TradeService;
import com.metagen.tradeservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService service;
    private final JwtUtil jwtUtil;

    @GetMapping("/history")
    public ResponseEntity<List<TradeDTO>> getHistory(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        List<TradeDTO> trades = service.getTradesByUser(username).stream().map(trade -> {
            TradeDTO dto = new TradeDTO();
            dto.setOrder_id(trade.getOrderId());
            dto.setUser(trade.getUser());
            dto.setSymbol(trade.getSymbol());
            dto.setSide(trade.getSide());
            dto.setVolume(trade.getVolume());
            dto.setExecution_price(trade.getExecutionPrice());
            dto.setStatus(trade.getStatus());
            dto.setTimestamp(trade.getTimestamp().toString());
            return dto;
        }).toList();

        return ResponseEntity.ok(trades);
    }

    @PostMapping
    public ResponseEntity<Void> saveTrade(@RequestBody TradeDTO dto) {
        service.saveTradeFromDTO(dto.getUser(), dto);
        return ResponseEntity.ok().build();
    }
}
