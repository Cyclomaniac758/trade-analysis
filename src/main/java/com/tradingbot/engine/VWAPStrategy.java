// src/main/java/com/tradingbot/engine/VWAPStrategy.java
package com.tradingbot.engine;

public class VWAPStrategy {
    public boolean shouldBuy(double currentPrice, double vwap) {
        return currentPrice < 0.98 * vwap;
    }

    public boolean shouldSell(double currentPrice, double vwap) {
        return currentPrice > 1.02 * vwap;
    }
}
