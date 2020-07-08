package com.idanch.data;

import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.Dish;
import com.idanch.data.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
             PreparedStatement stm = connection.prepareStatement(
                     "INSERT INTO orders (customer,status) VALUES (?,?);",
                     Statement.RETURN_GENERATED_KEYS))
        {
            stm.setString(1, customer);
            stm.setString(2, RestaurantOrder.OrderStatus.PENDING.name());
            stm.execute();

            ResultSet resultSet = stm.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public void addToOrder(long orderId, long dishId, int quantity) {

        Dish dishToAdd = menuDao.getDish(dishId);
        if (dishToAdd == null) {
            log.error("Dish is not on the menu - could not add to order (dish_id: " + dishId + ")");
            return;
        }
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD))
        {
            String contentsStrUpdated;
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

                order.addToOrder(dishId, quantity);
                contentsStrUpdated = contentsToStr(order);
            }

            try (PreparedStatement stm = connection.prepareStatement("UPDATE orders SET contents=? WHERE id=?")) {
                stm.setString(1, contentsStrUpdated);
                stm.setLong(2, orderId);
                stm.execute();
                log.info("Executed SQL UPDATE Statement on TABLE orders");
            }
        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
        }
    }

    @Override
    public Double calculateTotal(long orderId) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
            PreparedStatement stm = connection.prepareStatement("SELECT contents FROM orders WHERE id=?;"))
        {
            stm.setLong(1, orderId);
            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            if (resultSet.next()) {
                String contentsStr = resultSet.getString("contents");
                RestaurantOrder order = new RestaurantOrder();
                strToContents(contentsStr, order);

                Map<Long,Integer> contents = order.getContents();
                double total = 0.0;

                // calculate the total price
                for (long dishId: contents.keySet()) {
                    try (PreparedStatement stm1 = connection.prepareStatement("SELECT priceShekels FROM menu WHERE id=?")) {
                        stm1.setLong(1, dishId);
                        stm1.execute();
                        resultSet = stm1.getResultSet();
                        if (resultSet.next()) {
                            float price = resultSet.getFloat("priceShekels");
                            total += (price * contents.get(dishId));
                        }
                    }
                }
                return total;
            }

        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public RestaurantOrder.OrderStatus getOrderStatus(long orderId) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
            PreparedStatement stm = connection.prepareStatement("SELECT status FROM orders WHERE id=?")) {

            stm.setLong(1, orderId);
            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            if (resultSet.next()) {
                try {
                    return RestaurantOrder.OrderStatus.valueOf(resultSet.getString("status"));
                }catch(Exception exception) {
                    log.error(exception.getMessage());
                }
            }
        }catch(SQLException sqlException) {
            log.error(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public List<RestaurantOrder> getAllOrders() {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM orders;")) {

            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            List<RestaurantOrder> orders = new ArrayList<>();
            RestaurantOrder order;
            while (resultSet.next()) {
                order = new RestaurantOrder();
                long id = resultSet.getLong("id");
                String customer = resultSet.getString("customer");
                String contentsStr = resultSet.getString("contents");
                RestaurantOrder.OrderStatus status =
                        RestaurantOrder.OrderStatus.valueOf(resultSet.getString("status"));

                order.setId(id);
                order.setCustomer(customer);
                order.setStatus(status);

                if (contentsStr != null) {
                    strToContents(contentsStr, order);
                }
                orders.add(order);
            }
            return orders;

        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public void updateRestaurantOrder(long orderId, RestaurantOrder.OrderStatus status) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
            PreparedStatement stm = connection.prepareStatement("UPDATE orders SET status=? WHERE id=?")) {

            stm.setString(1, status.name());
            stm.setLong(2, orderId);
            stm.execute();

        }catch (SQLException sqlException) {
            log.error(sqlException.getMessage());
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
        if (contentsStr == null || contentsStr.equals("")) {
            return;
        }

        String[] orderItems = contentsStr.split(",");
        try{
            //last array item is empty (splitted string ends with ',')
            for (String orderItem : orderItems) {
                String[] orderPair = orderItem.split(":");
                long dishId = Long.parseLong(orderPair[0]);
                int quantity = Integer.parseInt(orderPair[1]);
                order.addToOrder(dishId, quantity);
            }
        }catch(Exception exception) {
            log.error("Failed to parse order contents field. Wrong format.");
        }
    }
}
