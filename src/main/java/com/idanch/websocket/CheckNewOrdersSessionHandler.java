package com.idanch.websocket;

import com.idanch.websocket.interfaces.BasicSessionHandler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckNewOrdersSessionHandler implements BasicSessionHandler {
    private List<Session> sessions = new ArrayList<>();

    public void add(Session session) {
        sessions.add(session);
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void sendMessage(String message) {
        for (Session session: sessions) {
            try {
                session.getBasicRemote().sendText(message);
            }catch(IOException ioe) {
                sessions.remove(session);
            }
        }
    }

    public void sendMessage(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        }catch(IOException ioe) {
            sessions.remove(session);
        }
    }
}
