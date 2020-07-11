package com.idanch.servlets;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.RestaurantOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getAllOrders")
public class GetAllOrdersServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
        List<RestaurantOrder> orders = ordersDao.getAllOrders();

        JSONArray ordersJSONArr = new JSONArray();
        JSONObject orderJSON;
        for (RestaurantOrder order: orders) {
            orderJSON = new JSONObject();

            orderJSON.put("id", order.getId());
            orderJSON.put("customer", order.getCustomer());
            orderJSON.put("status", order.getStatus());

            ordersJSONArr.put(orders.indexOf(order), orderJSON);
        }

        PrintWriter out = resp.getWriter();
        out.println(ordersJSONArr.toString());
    }
}
