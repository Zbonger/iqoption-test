package com.iqoption.websocket.rules;

import com.iqoption.websocket.WebSocketClient;
import com.iqoption.websocket.WebSocketClientImpl;
import org.junit.rules.ExternalResource;

import javax.websocket.Session;
import java.io.IOException;

public class WebSocketClientRule extends ExternalResource implements WebSocketClient{
    private final WebSocketClient client;

    public static WebSocketClientRule defaultWebSocketClient(){
        return new WebSocketClientRule(new WebSocketClientImpl("wss://iqoption.com/echo/websocket"));
    }

    public WebSocketClientRule(WebSocketClient client) {
        this.client = client;
    }

    protected void before() {
        client.connect();
    }

    protected void after() {
        try {
            client.getUserSession().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connect() {
        client.connect();
    }

    @Override
    public void sendMessage(String message) {
        client.sendMessage(message);
    }

    @Override
    public void setMessageHandler(MessageHandler handler) {
        client.setMessageHandler(handler);
    }

    @Override
    public Session getUserSession() {
        return client.getUserSession();
    }
}
