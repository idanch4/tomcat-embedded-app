package com.idanch.data.interfaces;

import com.idanch.representations.Dish;

public interface OrdersDao {
    Long newOrder(String customer);
    void addToOrder(Long orderId, Dish dish, Integer quantity);
}
