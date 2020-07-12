package com.idanch.websocket.interfaces;

import javax.websocket.Session;

public interface BasicSessionHandler {
    void add(Session session);
    void remove(Session session);
    void sendMessage(String message);
    void sendMessage(String message, Session session);
}
