package com.tradingbot.api;

import com.ib.client.Bar;
import com.tradingbot.api.Client.IBKRClient;
import com.tradingbot.database.Repositories.CandleRepository;
import com.tradingbot.database.entities.Candle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FetchMarketData implements IBKRClient.MarketDataCallback {
    
    @Autowired
    private CandleRepository candleRepository;
    
    @Autowired
    private IBKRClient ibkrClient;
    
    private final AtomicInteger requestIdCounter = new AtomicInteger(1000);
    
    public static final String[] TECH_STOCKS = {"AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "NVDA"};
    
    public void fetchHistoricalData(String symbol, String barSize, String whatToShow, 
                                   String durationString, boolean useRTH) {
        ibkrClient.setMarketDataCallback(this);
        
        int reqId = requestIdCounter.incrementAndGet();
        String endDateTime = "";
        
        ibkrClient.reqHistoricalData(reqId, symbol, "STK", "SMART", "USD", 
                                   endDateTime, durationString, barSize, whatToShow, 
                                   useRTH ? 1 : 0, 1);
        
        System.out.println("📊 Requesting " + barSize + " " + whatToShow + " data for " + symbol + " (Request ID: " + reqId + ")");
    }
    
    public void fetchTechStockData(String symbol, String durationString) {
        fetchHistoricalData(symbol, "1 min", "TRADES", durationString, true);
    }
    
    public void fetchAllTechStocks(String durationString) {
        for (String symbol : TECH_STOCKS) {
            fetchTechStockData(symbol, durationString);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Override
    public void onHistoricalData(int reqId, List<Bar> bars) {
        try {
            for (Bar bar : bars) {
                Candle candle = convertBarToCandle(bar);
                candleRepository.save(candle);
            }
            System.out.println("✅ Saved " + bars.size() + " candles for request " + reqId);
        } catch (Exception e) {
            System.err.println("❌ Error saving candles for request " + reqId + ": " + e.getMessage());
        }
    }
    
    @Override
    public void onError(int reqId, String errorMsg) {
        System.err.println("❌ Market data error for request " + reqId + ": " + errorMsg);
    }
    
    private Candle convertBarToCandle(Bar bar) {
        Candle candle = new Candle();
        candle.setTime(bar.time());
        candle.setOpen(bar.open());
        candle.setHigh(bar.high());
        candle.setLow(bar.low());
        candle.setClose(bar.close());
        candle.setVolume(bar.volume());
        candle.setWap(bar.wap());
        candle.setCount(bar.count());
        candle.setLastUpdateTime(LocalDateTime.now());
        candle.setIsRealTime(false);
        candle.setBarSize("1 min");
        candle.setWhatToShow("TRADES");
        candle.setUseRTH(true);
        candle.setFormatDate(1);
        return candle;
    }
    
    public List<Candle> getCandlesBySymbol(String symbol) {
        return candleRepository.findBySymbolOrderByTimeAsc(symbol);
    }
    
    public List<Candle> getCandlesBySymbolAndBarSize(String symbol, String barSize) {
        return candleRepository.findBySymbolAndBarSizeOrderByTimeAsc(symbol, barSize);
    }
}
