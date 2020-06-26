package com.idanch.data;

import com.idanch.representations.Dish;
import com.idanch.representations.RestaurantOrder;

public class IdansRestaurant {
    private static Menu menu = new Menu();
    static {
        menu.add(new Dish(1,"italian pizza", "classic, timeless, perfect.", "pizza", 60));
        menu.add(new Dish(2,"american pizza", "paparoni, cheese, dough.", "pizza", 30));
        menu.add(new Dish(3, "israeli pizza", "20 shekels.", "pizza", 20));

        menu.add(new Dish(4,"soup of the day", "chicken soup/mushroom soup/noodles soup", "soup", 25));
        menu.add(new Dish(5, "bread, soup, bread", "4 pieces of quality bread with quality soup", "soup", 40));
        menu.add(new Dish(6, "russian soup", "beet, potato, meat", "soup", 30));

        menu.add(new Dish(7, "shrimp & mushrooms", "shrimps, mushrooms, cream sauce", "seafood", 45));
    }

    public static Menu getMenu() {
        return menu;
    }
    public static RestaurantOrder newOrder() {
        return new RestaurantOrder(menu);
    }
}
