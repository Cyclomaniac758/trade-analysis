package com.tradingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tradingbot.api.Client.IBKRClient;

@SpringBootApplication
public class TradingToolApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TradingToolApplication.class, args);


        IBKRClient client = new IBKRClient();

        System.out.println("📡 Connecting to IBKR...");
        client.connect("127.0.0.1", 7496, 0); // Use 7496 for live trading

        Thread.sleep(5000); // wait for some messages

        if (client.isConnected()) {
            System.out.println("✅ Successfully connected to IBKR API.");
        } else {
            System.out.println("❌ Failed to connect.");
        }

        client.disconnect();
        System.out.println("🔌 Disconnected.");
    

    }
}