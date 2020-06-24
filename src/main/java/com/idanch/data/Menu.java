package com.idanch.data;

import java.util.*;

public class Menu {
    private List<Dish> menu = new ArrayList<>();

    public boolean add(Dish dish) {
        if (!isOnMenu(dish)) {
            menu.add(dish);
            return true;
        }
        return false;
    }

    public boolean isOnMenu(Dish dish) {
        return menu.contains(dish);
    }

    public List<Dish> findDishesByName(String query) {
        List<Dish> results = new LinkedList<>();
        for (Dish dish: menu) {
            if (dish.getName().contains(query.toLowerCase())) {
                results.add(dish);
            }
        }
        return results;
    }

    public Dish findDish(String dishName) {
        for (Dish dish : menu) {
            if (dish.getName().equals(dishName.toLowerCase())) {
                return dish;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu=" + menu +
                '}';
    }
}
