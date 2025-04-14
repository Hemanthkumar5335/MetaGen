package com.metagen.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class OrderResponse {
    private Long orderId;
    private String user;
    private String status;
}
