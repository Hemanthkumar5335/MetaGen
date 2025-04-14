package com.metagen.expertservice.dto;


import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String user;
    private String status;
}
