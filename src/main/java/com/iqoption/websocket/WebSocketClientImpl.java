package com.iqoption.websocket;


import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.net.URI;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */
@ClientEndpoint
public class WebSocketClientImpl implements WebSocketClient {
    private final String uri;
    private Session userSession = null;
    private MessageHandler messageHandler;

    public WebSocketClientImpl(String uri) {
        this.uri = uri;
    }

    @Override
    public void connect() {
        try {
            ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(uri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        System.out.println(userSession.getRequestURI());
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    @Override
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    @Override
    public void setMessageHandler(MessageHandler handler) {
        this.messageHandler = handler;
    }

    @Override
    public Session getUserSession() {
        return userSession;
    }
}