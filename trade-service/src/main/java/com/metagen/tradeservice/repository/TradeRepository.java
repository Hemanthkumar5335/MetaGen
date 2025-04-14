package com.metagen.tradeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metagen.tradeservice.entity.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByUser(String user);
}
