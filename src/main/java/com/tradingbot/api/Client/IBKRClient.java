package com.tradingbot.api.Client;

import com.ib.client.EClientSocket;
import com.ib.client.EWrapper;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;
import com.ib.client.DefaultEWrapper;
import com.ib.client.EJavaSignal;

public class IBKRClient {
    private final EClientSocket clientSocket;
    private final EJavaSignal signal;

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
            public void nextValidId(int orderId) {
                System.out.println("✅ Connected. Next valid order ID: " + orderId);
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
}
