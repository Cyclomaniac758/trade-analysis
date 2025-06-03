package com.tradingbot.config;

import com.tradingbot.api.Client.IBKRClient;
import com.tradingbot.api.MarketDataService;
import com.ib.client.DefaultEWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IBKRConfig {
    
    @Bean
    public IBKRClient ibkrClient(MarketDataService marketDataService) {
        return new IBKRClient() {
            @Override
            protected DefaultEWrapper createWrapper() {
                return new DefaultEWrapper() {
                    @Override
                    public void connectAck() {
                        System.out.println("✅ Connection acknowledged.");
                    }

                    @Override
                    public void error(Exception e) {
                        System.err.println("❌ Exception: " + e.getMessage());
                    }

                    @Override
                    public void error(int id, int errorCode, String errorMsg) {
                        System.err.println("❌ Error ID: " + id + ", Code: " + errorCode + ", Message: " + errorMsg);
                        marketDataService.onError(id, errorMsg);
                    }

                    @Override
                    public void nextValidId(int orderId) {
                        System.out.println("✅ Connected. Next valid order ID: " + orderId);
                    }
                    
                    @Override
                    public void historicalData(int reqId, com.ib.client.Bar bar) {
                        marketDataService.onHistoricalData(reqId, bar);
                    }
                    
                    @Override
                    public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {
                        marketDataService.onHistoricalDataEnd(reqId, startDateStr, endDateStr);
                    }
                };
            }
        };
    }
}
