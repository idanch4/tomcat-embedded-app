package com.idanch.servlets;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/processOrders")
public class ProcessOrdersServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(ProcessOrdersServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
        List<RestaurantOrder> orders = ordersDao.getAllOrders();
        if (orders == null) {
            orders = new ArrayList<>();
        }

        request.setAttribute("orders", orders);
        List<String> orderStatuses = Arrays
                .stream(RestaurantOrder.OrderStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        request.setAttribute("order_statuses", orderStatuses);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/processOrders.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        for (String paramName: params.keySet()) {
            if(paramName.startsWith("update_status")) {
                try {
                    long orderId = Long.parseLong(paramName.substring(paramName.lastIndexOf("_") + 1));
                    RestaurantOrder.OrderStatus status =
                            RestaurantOrder.OrderStatus.valueOf(params.get(paramName)[0]);
                    OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
                    ordersDao.updateRestaurantOrder(orderId, status);
                }catch (Exception exception) {
                    log.error(exception.getMessage());
                }
            }
        }
        response.sendRedirect("/processOrders");
    }
}
