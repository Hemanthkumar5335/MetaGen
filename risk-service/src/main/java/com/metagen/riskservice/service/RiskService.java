package com.metagen.riskservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.metagen.riskservice.dto.RiskCheckRequest;
import com.metagen.riskservice.dto.RiskCheckResponse;
import com.metagen.riskservice.entity.RiskCheck;
import com.metagen.riskservice.repository.RiskCheckRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiskService {

    private final RiskCheckRepository repo;

    public RiskCheckResponse checkRisk(String user, RiskCheckRequest request) {
        double price = 100.0; // Simulated fixed price for margin calculation
        double requiredMargin = (request.getVolume() * price) / request.getLeverage();
        double maxVolume = (request.getAccountBalance() * request.getLeverage()) / price;

        String status = requiredMargin <= request.getAccountBalance() ? "ALLOWED" : "REJECTED";

        RiskCheck risk = new RiskCheck(
                null, user, request.getSymbol(), request.getVolume(), request.getLeverage(),
                request.getAccountBalance(), maxVolume, status, LocalDateTime.now()
        );

        repo.save(risk);

        return new RiskCheckResponse(user, status, maxVolume);
    }
}
