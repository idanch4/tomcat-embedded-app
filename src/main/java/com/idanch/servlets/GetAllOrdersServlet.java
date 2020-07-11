package com.idanch.servlets;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.Dish;
import com.idanch.data.representations.RestaurantOrder;
import com.idanch.data.util.OrdersUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/getAllOrders")
public class GetAllOrdersServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
        MenuDao menuDao = MenuDaoFactory.getMenuDao();

        List<RestaurantOrder> orders = ordersDao.getAllOrders();
        List<Dish> menuItems = menuDao.getAllDishes();

        JSONArray ordersJSONArr = new JSONArray();
        JSONArray contentsJSONArr;
        JSONObject orderJSON;
        for (RestaurantOrder order: orders) {
            orderJSON = new JSONObject();

            orderJSON.put("id", order.getId());
            orderJSON.put("customer", order.getCustomer());
            orderJSON.put("status", order.getStatus());

            JSONObject menuItemJSON;
            contentsJSONArr = new JSONArray();
            Map<Long, Integer> contents = order.getContents();

            // insert all items on the order into the JSON
            int index = 0;
            for (Dish dish: menuItems) {
                if (contents.containsKey(dish.getId())) {
                    menuItemJSON = new JSONObject();

                    menuItemJSON.put("name", dish.getName());
                    menuItemJSON.put("category", dish.getCategory());
                    menuItemJSON.put("description", dish.getDescription());
                    menuItemJSON.put("priceShekels", dish.getPriceShekels());

                    contentsJSONArr.put(index, menuItemJSON);
                    index++;
                }
            }

            orderJSON.put("contents", contentsJSONArr);

            Double totalPrice = ordersDao.calculateTotal(order.getId());
            orderJSON.put("totalPrice", totalPrice);

            ordersJSONArr.put(orders.indexOf(order), orderJSON);
        }

        PrintWriter out = resp.getWriter();
        out.println(ordersJSONArr.toString());
    }
}
