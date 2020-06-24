package com.idanch.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdansRestaurant {
    private static Menu menu = new Menu();
    static {
        menu.add(new Dish("italian pizza", "classic, timeless, perfect.", 60));
        menu.add(new Dish("american pizza", "paparoni, cheese, dough.", 30));
        menu.add(new Dish("israeli pizza", "20 shekels.", 20));

        menu.add(new Dish("soup of the day", "chicken soup/mushroom soup/noodles soup", 25));
        menu.add(new Dish("bread, soup, bread", "4 pieces of quality bread with quality soup", 40));
        menu.add(new Dish("russian soup", "beet, potato, meat", 30));

        menu.add(new Dish("shrimp & mushrooms", "shrimps, mushrooms, cream sauce", 45));
    }

    public static Menu getMenu() {
        return menu;
    }

    public static RestaurantOrder newOrder() {
        return new RestaurantOrder(menu);
    }
}
