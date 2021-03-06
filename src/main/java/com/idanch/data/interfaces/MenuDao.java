package com.idanch.data.interfaces;

import com.idanch.data.representations.Dish;

import java.util.List;

public interface MenuDao {
    Dish getDish(Long id);
    List<Dish> getAllDishes();
    List<Dish> findDishes(String searchQuery);
}
