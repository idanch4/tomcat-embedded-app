package com.idanch.data;

import com.idanch.data.representations.Dish;
import com.idanch.data.representations.RestaurantOrder;
import com.idanch.data.util.OrdersUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbBootstrap {
    public static final Logger log = LoggerFactory.getLogger(DbBootstrap.class);

    private static final Menu menu = new Menu();
    public static final List<RestaurantOrder> orders = new ArrayList<>();

    static {
        menu.add(new Dish(11,"italian pizza", "classic, timeless, perfect.", "pizza", 60));
        menu.add(new Dish(21,"american pizza", "paparoni, cheese, dough.", "pizza", 30));
        menu.add(new Dish(31, "israeli pizza", "20 shekels.", "pizza", 20));

        menu.add(new Dish(41,"soup of the day", "chicken soup/mushroom soup/noodles soup", "soup", 25));
        menu.add(new Dish(51, "bread, soup, bread", "4 pieces of quality bread with quality soup", "soup", 40));
        menu.add(new Dish(61, "russian soup", "beet, potato, meat", "soup", 30));

        menu.add(new Dish(71, "shrimp & mushrooms", "shrimps, mushrooms, cream sauce", "seafood", 45));

        RestaurantOrder order = new RestaurantOrder(1, "Manager");
        order.addToOrder(11, 1);
        order.addToOrder(41, 2);

        orders.add(order);

        order = new RestaurantOrder(2, "Manager");
        order.addToOrder(51, 1);
        order.addToOrder(61, 1);

        orders.add(order);

        order = new RestaurantOrder(3, "Manager");
        order.addToOrder(71, 3);
        order.addToOrder(41, 1);
        order.addToOrder(31, 2);

        orders.add(order);

        order = new RestaurantOrder(4, "Manager");
        order.addToOrder(21, 2);
        order.addToOrder(51, 1);

        orders.add(order);

    }

    public static void initialize() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD)
        )
        {
            try (PreparedStatement stm = connection.prepareStatement("DROP TABLE IF EXISTS menu;")) {
                stm.execute();
            }

            try (PreparedStatement stm = connection.prepareStatement("DROP TABLE IF EXISTS orders;")) {
                stm.execute();
            }

            try (PreparedStatement stm = connection.prepareStatement(
                    "CREATE TABLE menu " +
                            "(id LONG PRIMARY KEY, name VARCHAR(50) NOT NULL,description VARCHAR(200), category VARCHAR(40),priceShekels FLOAT);")) {
                stm.execute();
            }

            try (PreparedStatement stm = connection.prepareStatement(
                    "CREATE TABLE orders " +
                            "(id LONG PRIMARY KEY auto_increment, customer VARCHAR(50) NOT NULL,contents VARCHAR(250),status VARCHAR(40));")) {
                stm.execute();
            }

            for (Dish dish: menu.getAllDishes()) {
                try (PreparedStatement stm = connection.prepareStatement(
                        "INSERT INTO menu " +
                                "(id,name,description,category,priceShekels) " +
                                "VALUES (?,?,?,?,?);")) {
                    stm.setLong(1, dish.getId());
                    stm.setString(2, dish.getName());
                    stm.setString(3, dish.getDescription());
                    stm.setString(4, dish.getCategory());
                    stm.setDouble(5, dish.getPriceShekels());
                    stm.execute();
                }
            }

            for (RestaurantOrder order: orders) {
                try (PreparedStatement stm = connection.prepareStatement(
                        "INSERT INTO orders " +
                                "(id,customer,status,contents) " +
                                "VALUES (?,?,?,?);")) {
                    stm.setLong(1, order.getId());
                    stm.setString(2, order.getCustomer());
                    stm.setString(3, order.getStatus());
                    stm.setString(4, OrdersUtil.contentsToStr(order));
                    stm.execute();
                }
            }
        }catch(SQLException sqlException) {
            throw new SQLException(sqlException);
        }
    }
}
