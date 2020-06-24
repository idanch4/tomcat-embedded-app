package com.idanch.data;

import java.util.HashMap;
import java.util.Map;

public class RestaurantOrder {
    private Map<String, Integer> order;
    private Menu menu;

    public RestaurantOrder(Menu menu) {
        this.menu = menu;
        this.order = new HashMap<>();
    }
    public int addToOrder(String dishName, int quantity) {

        if (!menu.isOnMenu(dishName)) {
            return 0;
        }

        if (order.containsKey(dishName)) {
            int newQuantity = order.get(dishName) + quantity;
            order.replace(dishName, newQuantity);
            return newQuantity;
        }

        order.put(dishName, quantity);
        return quantity;
    }

    public int calculateOrderPrice() {
        int totalPrice = 0;
        for (String dishName: order.keySet()) {
            Dish dishFromMenu = menu.getDish(dishName);
            if (dishFromMenu != null) {
                totalPrice += (dishFromMenu.getPriceShekels() * order.get(dishName));
            }
        }
        return totalPrice;
    }
}
