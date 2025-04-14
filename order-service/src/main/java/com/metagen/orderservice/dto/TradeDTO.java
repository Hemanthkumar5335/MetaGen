package com.metagen.orderservice.dto;

import lombok.Data;

@Data
public class TradeDTO {
    private String order_id;
    private String user;
    private String symbol;
    private String side;
    private double volume;
    private double execution_price;
    private String status;
    private String timestamp;
}
