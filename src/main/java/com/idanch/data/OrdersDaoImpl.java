package com.idanch.data;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.Dish;
import com.idanch.data.representations.FullOrder;
import com.idanch.data.representations.RestaurantOrder;
import com.idanch.data.util.OrdersUtil;
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
                OrdersUtil.strToContents(contentsStr, order);

                order.addToOrder(dishId, quantity);
                contentsStrUpdated = OrdersUtil.contentsToStr(order);
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
                OrdersUtil.strToContents(contentsStr, order);

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
    public FullOrder getOrder(long id) {
        try(Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM orders WHERE id=?")) {

            stm.setLong(1, id);
            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            if (resultSet.next()) {
                RestaurantOrder order = new RestaurantOrder();
                try {
                    order.setCustomer(resultSet.getString("customer"));
                    order.setId(resultSet.getLong("id"));
                    order.setStatus(RestaurantOrder.OrderStatus.valueOf(resultSet.getString("status")));

                    OrdersUtil.strToContents(resultSet.getString("contents"), order);

                    FullOrder fullOrder = new FullOrder();
                    fullOrder.setId(order.getId());
                    fullOrder.setCustomer(order.getCustomer());
                    fullOrder.setStatus(FullOrder.OrderStatus.valueOf(order.getStatus()));

                    MenuDao menuDao = MenuDaoFactory.getMenuDao();
                    for (long orderId: order.getContents().keySet()) {
                        fullOrder.addToOrder(menuDao.getDish(orderId), order.getContents().get(orderId));
                    }
                    return fullOrder;
                }catch (Exception exception) {
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
                    OrdersUtil.strToContents(contentsStr, order);
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
    public List<RestaurantOrder> getAllOrdersFrom(long id) {
        try (Connection connection = DriverManager.getConnection(
                JdbcConfig.H2_URL,
                JdbcConfig.DB_USERNAME,
                JdbcConfig.DB_PASSWORD);
             PreparedStatement stm = connection.prepareStatement("SELECT * FROM orders WHERE id > ?")) {

            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            List<RestaurantOrder> orders = new ArrayList<>();
            RestaurantOrder order;
            while (resultSet.next()) {
                order = new RestaurantOrder();
                long orderId = resultSet.getLong("id");
                String customer = resultSet.getString("customer");
                String contentsStr = resultSet.getString("contents");
                RestaurantOrder.OrderStatus status =
                        RestaurantOrder.OrderStatus.valueOf(resultSet.getString("status"));

                order.setId(orderId);
                order.setCustomer(customer);
                order.setStatus(status);

                if (contentsStr != null) {
                    OrdersUtil.strToContents(contentsStr, order);
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
}
