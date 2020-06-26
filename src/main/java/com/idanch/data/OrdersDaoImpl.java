package com.idanch.data;

import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.representations.Dish;
import com.idanch.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;

public class OrdersDaoImpl implements OrdersDao {
    public static final Logger log = LoggerFactory.getLogger(OrdersDaoImpl.class);

    private final MenuDao menuDao;

    public OrdersDaoImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public Long newOrder(String customer) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_CONNECTION_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
             PreparedStatement stm = connection.prepareStatement("INSERT INTO orders (customer, status) VALUES (?,?);"))
        {
            stm.setString(1, customer);
            stm.setString(2, "PENDING");
            if(stm.execute()) {
                ResultSet resultSet = stm.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                }
            }
            return null;
        }catch (SQLException sqlException) {
            log.error(sqlException.getSQLState());
            return null;
        }
    }

    @Override
    public void addToOrder(Long orderId, Dish dish, Integer quantity) {

        Dish dishToAdd = menuDao.getDish(dish.getId());
        if (dishToAdd == null) {
            log.error("Dish is not on the menu - could not add to order (" + dish + ")");
            return;
        }
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_CONNECTION_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);)
        {
            String contentsStrUpdated = null;
            try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM orders WHERE id=?;"))
            {
                log.info("Executing SQL SELECT Statement on Table orders");
                stm.setLong(1, orderId);
                stm.execute();
                ResultSet resultSet = stm.getResultSet();
                if (!resultSet.next()) {
                    log.error("Order " + orderId + " does not exist. Create a new order first.");
                }

                RestaurantOrder order = new RestaurantOrder();
                String contentsStr = resultSet.getString("contents");
                strToContents(contentsStr, order);

                order.addToOrder(dish.getId(), quantity);
                contentsStrUpdated = contentsToStr(order);
            }

            try (PreparedStatement stm = connection.prepareStatement("UPDATE orders SET (contents=?) WHERE id=?;")) {
                stm.setString(1, contentsStrUpdated);
                stm.setLong(2, orderId);
                stm.execute();
                log.info("Executed SQL UPDATE Statement on TABLE orders");
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getSQLState());
        }
    }

    private String contentsToStr(RestaurantOrder order) {
        StringBuilder str = new StringBuilder();
        Map<Long,Integer> contents = order.getContents();
        for (Map.Entry<Long,Integer> entry: contents.entrySet()){
            str.append(entry.getKey());
            str.append(":");
            str.append(entry.getValue());
            str.append(",");
        }
        return str.toString();
    }

    public void strToContents(String contentsStr, RestaurantOrder order) {
        String[] orderItems = contentsStr.split(",");
        try{
            //last array item is empty (splitted string ends with ',')
            for (int i = 0; i< orderItems.length - 1; i++) {
                String[] orderPair = orderItems[i].split(":");
                long dishId = Long.parseLong(orderPair[0]);
                int quantity = Integer.parseInt(orderPair[1]);
                order.addToOrder(dishId, quantity);
            }
        }catch(Exception exception) {
            log.error("Failed to parse order contents field. Wrong format.");
        }
    }
}
