package com.idanch.data;

public class IdansRestaurant {
    private static Menu menu = new Menu();
    static {
        menu.add(new Dish("italian pizza", "classic, timeless, perfect."));
        menu.add(new Dish("american pizza", "paparoni, cheese, dough."));
        menu.add(new Dish("israeli pizza", "20 shekels."));

        menu.add(new Dish("soup of the day", "chicken soup/mushroom soup/noodles soup"));
        menu.add(new Dish("bread, soup, bread", "4 pieces of quality bread with quality soup"));
        menu.add(new Dish("russian soup", "beet, potato, meat"));

        menu.add(new Dish("shrimp & mushrooms", "shrimps, mushrooms, cream sauce"));
    }

    public static Menu getMenu() {
        return menu;
    }
}
