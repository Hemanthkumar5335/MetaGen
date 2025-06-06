package com.metagen.marketservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Subscription {
    private List<String> symbols;
    private long intervalSeconds;
}
