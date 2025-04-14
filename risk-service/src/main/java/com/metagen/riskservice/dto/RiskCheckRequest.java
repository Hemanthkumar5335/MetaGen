package com.metagen.riskservice.dto;

import lombok.Data;

@Data
public class RiskCheckRequest {
    private String symbol;
    private double volume;
    private int leverage;
    private double accountBalance;
}
