package com.idanch.websocket;

import com.idanch.websocket.interfaces.BasicSessionHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/checkNewOrders")
public class CheckNewOrdersWebSocket {
    private static final BasicSessionHandler sessionHandler =
            CheckNewOrdersSessionHandlerFactory.getCheckNewOrdersSessionHandler();

    @OnOpen
    public void open(Session session) {
        sessionHandler.add(session);
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.remove(session);
    }

    @OnMessage
    public void message(String message, Session session) {
        //TODO:: handle client web socket message
    }
}
