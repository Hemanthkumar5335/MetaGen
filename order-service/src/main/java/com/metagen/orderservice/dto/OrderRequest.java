package com.metagen.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String symbol;
    private double volume;
    private String side;         // buy or sell
    private String orderType;    // market
    private double takeProfit;
    private double stopLoss;
}
