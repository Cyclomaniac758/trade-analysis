package com.tradingbot.engine;

import java.util.List;

import com.tradingbot.database.entities.Candle;
import com.tradingbot.database.entities.Trade;

public interface StrategyEngine {
    Trade generateSignal(List<Candle> candles);
}
