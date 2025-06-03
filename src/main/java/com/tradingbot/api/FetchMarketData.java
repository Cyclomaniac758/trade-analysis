package com.tradingbot.api;

import com.ib.client.Bar;
import com.tradingbot.api.Client.IBKRClient;
import com.tradingbot.database.entities.Candle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CompletableFuture;

@Service
public class FetchMarketData implements IBKRClient.MarketDataCallback {
    
    @Autowired
    private IBKRClient ibkrClient;
    
    private final AtomicInteger requestIdCounter = new AtomicInteger(1000);
    private final Map<Integer, List<Candle>> inMemoryData = new ConcurrentHashMap<>();
    private final Map<Integer, CompletableFuture<List<Candle>>> pendingRequests = new ConcurrentHashMap<>();
    private final Map<Integer, String> requestSymbols = new ConcurrentHashMap<>();
    
    public static final String[] TECH_STOCKS = {"AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "NVDA"};
    
    public CompletableFuture<List<Candle>> fetchHistoricalData(String symbol, String barSize, String whatToShow, 
                                                              String durationString, boolean useRTH) {
        ibkrClient.setMarketDataCallback(this);
        
        int reqId = requestIdCounter.incrementAndGet();
        String endDateTime = "";
        
        CompletableFuture<List<Candle>> future = new CompletableFuture<>();
        pendingRequests.put(reqId, future);
        requestSymbols.put(reqId, symbol);
        
        ibkrClient.reqHistoricalData(reqId, symbol, "STK", "SMART", "USD", 
                                   endDateTime, durationString, barSize, whatToShow, 
                                   useRTH ? 1 : 0, 1);
        
        System.out.println("📊 Requesting " + barSize + " " + whatToShow + " data for " + symbol + " (Request ID: " + reqId + ")");
        return future;
    }
    
    public CompletableFuture<List<Candle>> fetchTechStockData(String symbol, String durationString) {
        return fetchHistoricalData(symbol, "1 min", "TRADES", durationString, true);
    }
    
    public CompletableFuture<Map<String, List<Candle>>> fetchAllTechStocks(String durationString) {
        Map<String, CompletableFuture<List<Candle>>> futures = new ConcurrentHashMap<>();
        
        for (String symbol : TECH_STOCKS) {
            futures.put(symbol, fetchTechStockData(symbol, durationString));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    Map<String, List<Candle>> results = new ConcurrentHashMap<>();
                    futures.forEach((symbol, future) -> {
                        try {
                            results.put(symbol, future.get());
                        } catch (Exception e) {
                            System.err.println("❌ Failed to get data for " + symbol + ": " + e.getMessage());
                            results.put(symbol, new ArrayList<>());
                        }
                    });
                    return results;
                });
    }
    
    @Override
    public void onHistoricalData(int reqId, List<Bar> bars) {
        try {
            List<Candle> candles = new ArrayList<>();
            String symbol = requestSymbols.get(reqId);
            
            for (Bar bar : bars) {
                Candle candle = convertBarToCandle(bar, symbol);
                candles.add(candle);
            }
            
            inMemoryData.put(reqId, candles);
            CompletableFuture<List<Candle>> future = pendingRequests.get(reqId);
            if (future != null) {
                future.complete(candles);
            }
            
            System.out.println("✅ Loaded " + bars.size() + " candles in memory for " + symbol + " (Request ID: " + reqId + ")");
            
            pendingRequests.remove(reqId);
            requestSymbols.remove(reqId);
            
        } catch (Exception e) {
            System.err.println("❌ Error processing candles for request " + reqId + ": " + e.getMessage());
            CompletableFuture<List<Candle>> future = pendingRequests.get(reqId);
            if (future != null) {
                future.completeExceptionally(e);
            }
        }
    }
    
    @Override
    public void onError(int reqId, String errorMsg) {
        System.err.println("❌ Market data error for request " + reqId + ": " + errorMsg);
        CompletableFuture<List<Candle>> future = pendingRequests.get(reqId);
        if (future != null) {
            future.completeExceptionally(new RuntimeException(errorMsg));
        }
        pendingRequests.remove(reqId);
        requestSymbols.remove(reqId);
    }
    
    private Candle convertBarToCandle(Bar bar, String symbol) {
        Candle candle = new Candle();
        candle.setTime(bar.time());
        candle.setOpen(bar.open());
        candle.setHigh(bar.high());
        candle.setLow(bar.low());
        candle.setClose(bar.close());
        candle.setVolume(bar.volume());
        candle.setWap(bar.wap());
        candle.setCount(bar.count());
        candle.setSymbol(symbol);
        candle.setLastUpdateTime(LocalDateTime.now());
        candle.setIsRealTime(false);
        candle.setBarSize("1 min");
        candle.setWhatToShow("TRADES");
        candle.setUseRTH(true);
        candle.setFormatDate(1);
        return candle;
    }
    
    public List<Candle> getInMemoryData(int requestId) {
        return inMemoryData.get(requestId);
    }
    
    public void clearInMemoryData(int requestId) {
        inMemoryData.remove(requestId);
    }
    
    public void clearAllInMemoryData() {
        inMemoryData.clear();
    }
}
