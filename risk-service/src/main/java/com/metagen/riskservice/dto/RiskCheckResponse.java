package com.metagen.riskservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskCheckResponse {
    private String user;
    private String status;      // ALLOWED or REJECTED
    private double maxVolume;
}
