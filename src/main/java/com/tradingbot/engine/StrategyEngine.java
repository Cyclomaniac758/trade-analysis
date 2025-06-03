package com.tradingbot.engine;

import java.util.List;

import com.tradingbot.database.entities.Candle;
import com.tradingbot.database.entities.Position;
import com.tradingbot.database.entities.StrategyResult;

public interface StrategyEngine {
    StrategyResult generateSignal(List<Candle> candles, double currentPrice, List<Position> openPositions);
}
