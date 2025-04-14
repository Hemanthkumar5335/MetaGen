package com.metagen.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskCheckRequest {
    private String symbol;
    private double volume;
    private int leverage;
    private double accountBalance;
}
