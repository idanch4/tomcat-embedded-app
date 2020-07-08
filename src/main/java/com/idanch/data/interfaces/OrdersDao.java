package com.idanch.data.interfaces;

import com.idanch.data.representations.RestaurantOrder;

import java.util.List;

public interface OrdersDao {
    Long newOrder(String customer);
    void addToOrder(long orderId, long dishId, int quantity);
    RestaurantOrder.OrderStatus getOrderStatus(long orderId);
    Double calculateTotal(long orderId);
    RestaurantOrder getOrder(long id);
    List<RestaurantOrder> getAllOrders();
    void updateRestaurantOrder(long orderId, RestaurantOrder.OrderStatus status);
}
