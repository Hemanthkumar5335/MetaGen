package com.metagen.tradeservice.dto;


import lombok.Data;

@Data
public class TradeDTO {
    private String order_id;
    private String user; // Required for auto-saving from order-service
    private String symbol;
    private String side;
    private double volume;
    private double execution_price;
    private String status;
    private String timestamp; // ISO 8601 format
}
