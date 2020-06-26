package com.idanch.data;

import com.idanch.representations.Dish;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private Map<String, Dish> menu = new HashMap<>();

    public boolean add(Dish dish) {
        if (!menu.containsKey(dish.getName())) {
            menu.put(dish.getName(), dish);
            return true;
        }
        return false;
    }

    public boolean isOnMenu(String dishName) {
        return menu.containsKey(dishName);
    }

    public List<Dish> findDishesByName(String query) {
        return menu.values()
                .stream()
                .filter(v -> v.getName().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Dish getDish(String dishName) {
        return menu.get(dishName);
    }

    public List<Dish> getAllDishes() {
        return new ArrayList<>(menu.values());
    }

    public List<String> getAllDishNames() {
        return new ArrayList<>(menu.keySet());
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu=" + menu +
                '}';
    }
}
