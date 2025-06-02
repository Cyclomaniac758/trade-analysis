package com.tradingbot.database.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private LocalDateTime tradeTime;

    private double price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    public enum TradeType {
        BUY, SELL
    }

    // Constructors, getters and setters

    public Trade() {}

    public Trade(String symbol, LocalDateTime tradeTime, double price, int quantity, TradeType tradeType) {
        this.symbol = symbol;
        this.tradeTime = tradeTime;
        this.price = price;
        this.quantity = quantity;
        this.tradeType = tradeType;
    }

    // getters/setters omitted for brevity
}
