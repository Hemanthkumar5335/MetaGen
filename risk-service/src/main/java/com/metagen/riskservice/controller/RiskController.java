package com.metagen.riskservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metagen.riskservice.dto.RiskCheckRequest;
import com.metagen.riskservice.dto.RiskCheckResponse;
import com.metagen.riskservice.service.RiskService;
import com.metagen.riskservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;
    private final JwtUtil jwtUtil;

    @PostMapping("/check")
    public ResponseEntity<RiskCheckResponse> check(@RequestBody RiskCheckRequest request,
                                                   @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        RiskCheckResponse response = riskService.checkRisk(username, request);
        return ResponseEntity.ok(response);
    }
}
