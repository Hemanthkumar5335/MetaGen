package com.metagen.expertservice.dto;



public class SignalRequest {
    private String eaId;       // Unique identifier for the EA
    private String symbol;     // Symbol for the pair (e.g., EUR/USD)
    private String orderType;  // Type of order, e.g., market, limit, etc.
    private String side;       // Buy or Sell
    private double volume;     // Volume of the order

    // Getters and Setters
    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
