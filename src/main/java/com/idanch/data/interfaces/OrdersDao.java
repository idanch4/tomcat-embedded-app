package com.idanch.data.interfaces;

public interface OrdersDao {
    Long newOrder(String customer);
    void addToOrder(long orderId, long dishId, int quantity);
    Double calculateTotal(long orderId);
}
