package com.iqoption.websocket;

import com.google.gson.Gson;
import com.iqoption.websocket.beans.Message;

import javax.websocket.Session;

public interface WebSocketClient {
    void connect();

    void sendMessage(String message);

    default void sendMessage(Message message) {
        sendMessage(new Gson().toJson(message));
    }

    void setMessageHandler(WebSocketClientImpl.MessageHandler handler);

    Session getUserSession();

    interface MessageHandler {
        void handleMessage(String message);
    }
}
