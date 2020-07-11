package com.idanch.data.representations;

import java.util.HashMap;
import java.util.Map;

public class RestaurantOrder {
    private long id;
    private final Map<Long,Integer> contents;
    private String customer;
    private OrderStatus status;

    public RestaurantOrder() {
        this.contents = new HashMap<>();
    }
    public RestaurantOrder(long id, String customer) {
        this.id = id;
        this.customer = customer;
        this.contents = new HashMap<>();
        this.status = OrderStatus.NOT_ORDERED;
    }

    public void addToOrder(long dishId, int quantity) {
        if (contents.containsKey(dishId)) {
            int newQuantity = contents.get(dishId) + quantity;
            contents.replace(dishId, newQuantity);
        } else {
            contents.put(dishId, quantity);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Long, Integer> getContents() {
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
