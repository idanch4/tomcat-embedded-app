package com.idanch.data;

import com.idanch.data.interfaces.OrdersDao;
import com.idanch.representations.Dish;

public class OrdersDaoImpl implements OrdersDao {
    @Override
    public Long newOrder(String customer) {
        return null;
    }

    @Override
    public void addToOrder(Long orderId, Dish dish, Integer quantity) {

    }
}
