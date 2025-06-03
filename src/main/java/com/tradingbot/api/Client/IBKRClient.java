package com.tradingbot.api.Client;

import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.ib.client.DefaultEWrapper;
import com.ib.client.EJavaSignal;
import com.ib.client.Contract;
import com.ib.client.Bar;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class IBKRClient {
    private final EClientSocket clientSocket;
    private final EJavaSignal signal;
    private final Map<Integer, List<Bar>> historicalDataMap = new ConcurrentHashMap<>();
    private final Map<Integer, Boolean> requestCompleteMap = new ConcurrentHashMap<>();
    private MarketDataCallback callback;
    
    public interface MarketDataCallback {
        void onHistoricalData(int reqId, List<Bar> bars);
        void onError(int reqId, String errorMsg);
    }
    
    public void setMarketDataCallback(MarketDataCallback callback) {
        this.callback = callback;
    }

    public IBKRClient() {
        signal = new EJavaSignal();
        clientSocket = new EClientSocket(new DefaultEWrapper() {
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
                if (callback != null) {
                    callback.onError(id, errorMsg);
                }
            }

            @Override
            public void nextValidId(int orderId) {
                System.out.println("✅ Connected. Next valid order ID: " + orderId);
            }
            
            @Override
            public void historicalData(int reqId, Bar bar) {
                List<Bar> bars = historicalDataMap.get(reqId);
                if (bars != null) {
                    bars.add(bar);
                }
            }
            
            @Override
            public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {
                requestCompleteMap.put(reqId, true);
                List<Bar> bars = historicalDataMap.get(reqId);
                if (callback != null && bars != null) {
                    callback.onHistoricalData(reqId, bars);
                }
                historicalDataMap.remove(reqId);
                requestCompleteMap.remove(reqId);
            }
        }, signal);
    }

    public void connect(String host, int port, int clientId) {
        clientSocket.eConnect(host, port, clientId);

        // Start a background thread to read messages
        EReader reader = new EReader(clientSocket, signal);
        reader.start();
        new Thread(() -> {
            while (clientSocket.isConnected()) {
                signal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.err.println("❌ Message processing error: " + e.getMessage());
                }
            }
        }).start();
    }

    public boolean isConnected() {
        return clientSocket.isConnected();
    }

    public void disconnect() {
        clientSocket.eDisconnect();
    }
    
    public void reqHistoricalData(int reqId, String symbol, String secType, String exchange, 
                                 String currency, String endDateTime, String durationString, 
                                 String barSizeSetting, String whatToShow, int useRTH, int formatDate) {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType(secType);
        contract.exchange(exchange);
        contract.currency(currency);
        
        historicalDataMap.put(reqId, new ArrayList<>());
        requestCompleteMap.put(reqId, false);
        
        clientSocket.reqHistoricalData(reqId, contract, endDateTime, durationString, 
                                     barSizeSetting, whatToShow, useRTH, formatDate, false, null);
    }
}
