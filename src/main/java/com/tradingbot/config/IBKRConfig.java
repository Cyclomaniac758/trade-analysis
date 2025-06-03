package com.tradingbot.config;

import com.tradingbot.api.Client.IBKRClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IBKRConfig {
    
    @Bean
    public IBKRClient ibkrClient() {
        return new IBKRClient();
    }
}
