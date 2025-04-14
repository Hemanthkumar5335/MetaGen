package com.metagen.tradeservice.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.metagen.tradeservice.dto.TradeDTO;
import com.metagen.tradeservice.entity.Trade;
import com.metagen.tradeservice.repository.TradeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository repo;

    public List<Trade> getTradesByUser(String username) {
        return repo.findByUser(username);
    }

    public void saveTradeFromDTO(String username, TradeDTO dto) {
        Trade trade = new Trade();
        trade.setUser(username);
        trade.setOrderId(dto.getOrder_id());
        trade.setSymbol(dto.getSymbol());
        trade.setSide(dto.getSide());
        trade.setVolume(dto.getVolume());
        trade.setExecutionPrice(dto.getExecution_price());
        trade.setStatus(dto.getStatus());

        String cleanedTime = dto.getTimestamp().replace("Z", "");
        trade.setTimestamp(LocalDateTime.parse(cleanedTime, DateTimeFormatter.ISO_DATE_TIME));

        repo.save(trade);
    }
}
