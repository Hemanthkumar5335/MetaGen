package com.metagen.tradeservice.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String user;
    private String symbol;
    private String side;
    private double volume;
    private double executionPrice;
    private String status;
    private LocalDateTime timestamp;
}
