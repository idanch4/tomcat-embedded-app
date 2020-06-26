package com.idanch.data.factories;

import com.idanch.data.OrdersDaoImpl;
import com.idanch.data.interfaces.OrdersDao;

public class OrdersDaoFactory {
    private static OrdersDao ordersDao;

    public static OrdersDao getOrdersDao() {
        if (ordersDao != null) {
            return ordersDao;
        }
        return new OrdersDaoImpl();
    }
}