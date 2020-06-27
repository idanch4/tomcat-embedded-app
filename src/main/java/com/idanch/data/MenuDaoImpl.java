package com.idanch.data;

import com.idanch.data.interfaces.MenuDao;
import com.idanch.representations.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDaoImpl implements MenuDao {
    Logger log = LoggerFactory.getLogger(MenuDao.class);

    @Override
    public Dish getDish(Long id) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_CONNECTION_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD)) {
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM menu WHERE id=?;")) {
                stm.setLong(1, id);
                stm.execute();
                log.info("Executed SQL SELECT Statement on Table menu");
                ResultSet resultSet = stm.getResultSet();

                if (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setId(resultSet.getLong("id"));
                    dish.setName(resultSet.getString("name"));
                    dish.setDescription(resultSet.getString("description"));
                    dish.setCategory(resultSet.getString("category"));
                    dish.setPriceShekels(resultSet.getFloat("priceShekels"));

                    return dish;
                }

                return null;
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getSQLState());
            return null;
        }
    }

    @Override
    public List<Dish> getAllDishes() {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_CONNECTION_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD)) {
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM menu;")) {
                stm.execute();
                log.info("Executed SQL SELECT Statement on Table menu");
                ResultSet resultSet = stm.getResultSet();

                List<Dish> dishes = new ArrayList<>();
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setId(resultSet.getLong("id"));
                    dish.setName(resultSet.getString("name"));
                    dish.setDescription(resultSet.getString("description"));
                    dish.setCategory(resultSet.getString("category"));
                    dish.setPriceShekels(resultSet.getFloat("priceShekels"));

                    dishes.add(dish);
                }

                return dishes;
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Dish> findDishes(String searchQuery) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_CONNECTION_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD)) {
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM menu WHERE name LIKE ? OR description LIKE ?")) {
                stm.setString(1,"%" + searchQuery +"%");
                stm.setString(2,"%" + searchQuery +"%");

                ResultSet resultSet = stm.executeQuery();
                log.info("Executed SQL SELECT Statement on Table menu");

                List<Dish> dishes = new ArrayList<>();
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setId(resultSet.getLong("id"));
                    dish.setName(resultSet.getString("name"));
                    dish.setDescription(resultSet.getString("description"));
                    dish.setCategory(resultSet.getString("category"));
                    dish.setPriceShekels(resultSet.getFloat("priceShekels"));

                    dishes.add(dish);
                }

                return dishes;
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
            return new ArrayList<>();
        }
    }
}
