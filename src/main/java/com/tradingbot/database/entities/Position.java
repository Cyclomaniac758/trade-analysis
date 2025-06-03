package com.tradingbot.database.entities;

import java.time.LocalDateTime;

public class Position {
    private String symbol;
    private int quantity;
    private double entryPrice;
    private boolean isLong;
    private LocalDateTime entryTime;
    private long tradeId;
}
