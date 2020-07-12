package com.idanch.data.representations;

import java.util.HashMap;
import java.util.Map;

/*
    Replaces @RestaurantOrder class.
    older class still exists for now, until all code will use this class
 */
public class FullOrder {
    private long id;
    private final Map<Dish,Integer> contents;
    private String customer;
    private OrderStatus status;

    public FullOrder() {
        this.contents = new HashMap<>();
    }
    public FullOrder(long id, String customer) {
        this.id = id;
        this.customer = customer;
        this.contents = new HashMap<>();
        this.status = OrderStatus.NOT_ORDERED;
    }

    public void addToOrder(Dish dish, int quantity) {
        if (contents.containsKey(dish)) {
            int newQuantity = contents.get(dish) + quantity;
            contents.replace(dish, newQuantity);
        } else {
            contents.put(dish, quantity);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Dish, Integer> getContents() {
        return new HashMap<>(contents);
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(OrderStatus status){
        this.status = status;
    }

    public enum OrderStatus {
        NOT_ORDERED,
        PENDING,
        ORDER_ACCEPTED,
        PAYMENT_RECEIVED,
        ORDER_PREPARED,
        READY;
    }
}
