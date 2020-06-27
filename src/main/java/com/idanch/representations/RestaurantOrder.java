package com.idanch.representations;

import java.util.HashMap;
import java.util.Map;

public class RestaurantOrder {
    private long id;
    private final Map<Long,Integer> contents;
    private String customer;
    private String status;

    public void addToOrder(long dishId, int quantity) {
        if (contents.containsKey(dishId)) {
            int newQuantity = contents.get(dishId) + quantity;
            contents.replace(dishId, newQuantity);
        } else {
            contents.put(dishId, quantity);
        }
    }

    public RestaurantOrder() {
        this.contents = new HashMap<>();
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}