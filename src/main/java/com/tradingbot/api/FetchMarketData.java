package com.tradingbot.api;

import com.tradingbot.api.MarketDataService;
import com.tradingbot.database.entities.Candle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class FetchMarketData {
    
    @Autowired
    private MarketDataService marketDataService;
    
    public static final String[] TECH_STOCKS = {"AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "NVDA"};
    
    public CompletableFuture<List<Candle>> fetchHistoricalData(String symbol, String barSize, String whatToShow, 
                                                              String durationString, boolean useRTH) {
        return marketDataService.fetchHistoricalData(symbol, barSize, whatToShow, durationString, useRTH);
    }
    
    public CompletableFuture<List<Candle>> fetchTechStockData(String symbol, String durationString) {
        return marketDataService.fetchTechStockData(symbol, durationString);
    }
    
    public CompletableFuture<Map<String, List<Candle>>> fetchAllTechStocks(String durationString) {
        return marketDataService.fetchAllTechStocks(durationString);
    }
    
    public List<Candle> getInMemoryData(int requestId) {
        return marketDataService.getInMemoryData(requestId);
    }
    
    public void clearInMemoryData(int requestId) {
        marketDataService.clearInMemoryData(requestId);
    }
    
    public void clearAllInMemoryData() {
        marketDataService.clearAllInMemoryData();
    }
}
