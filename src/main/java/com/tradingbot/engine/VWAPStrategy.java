// src/main/java/com/tradingbot/engine/VWAPStrategy.java
package com.tradingbot.engine;

import java.util.List;

import com.tradingbot.database.entities.Candle;
import com.tradingbot.database.entities.Position;
import com.tradingbot.database.entities.StrategyResult;

public class VWAPStrategy implements StrategyEngine {
    public boolean shouldBuy(double currentPrice, double vwap) {
        return currentPrice < 0.98 * vwap;
    }

    public boolean shouldSell(double currentPrice, double vwap) {
        return currentPrice > 1.02 * vwap;
    }

    public StrategyResult generateSignal(List<Candle> candles, double currentPrice, List<Position> openPositions){
        return new StrategyResult();
    }
}
