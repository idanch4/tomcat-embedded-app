package com.idanch.data;

import com.idanch.representations.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final Map<String, Dish> menu = new HashMap<>();

    public boolean add(Dish dish) {
        if (!menu.containsKey(dish.getName())) {
            menu.put(dish.getName(), dish);
            return true;
        }
        return false;
    }

    public List<Dish> getAllDishes() {
        return new ArrayList<>(menu.values());
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu=" + menu +
                '}';
    }
}
