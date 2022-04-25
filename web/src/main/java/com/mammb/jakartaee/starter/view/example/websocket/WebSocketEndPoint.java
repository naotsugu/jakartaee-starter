package com.mammb.jakartaee.starter.view.example.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.util.logging.Logger;

@ServerEndpoint("/endpoint")
public class WebSocketEndPoint {

    private static final Logger log = Logger.getLogger(WebSocketEndPoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        log.info("#### @OnOpen[" + session.getId() + "]");
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        log.info("#### @OnMessage[" + message + "][" + session.getId() + "]");
        return message;
    }

    @OnError
    public void onError(Session session, Throwable cause) {
        log.info("#### @OnError[" + cause.getMessage() + "]" + session.getId() + "]");
    }

    @OnClose
    public void onClose(Session session) {
        log.info("#### @OnClose[" + session.getId() + "]");
    }
}
