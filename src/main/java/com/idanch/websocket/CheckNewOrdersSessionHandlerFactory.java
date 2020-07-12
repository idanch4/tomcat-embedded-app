package com.idanch.websocket;

import com.idanch.websocket.interfaces.BasicSessionHandler;

public class CheckNewOrdersSessionHandlerFactory {
    private static BasicSessionHandler instance;

    public static BasicSessionHandler getCheckNewOrdersSessionHandler() {
        if (instance == null) {
            instance = new CheckNewOrdersSessionHandler();
        }
        return instance;
    }
}
