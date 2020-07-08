package com.idanch.servlets;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(UpdateStatusServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long orderId = Long.parseLong(req.getParameter("id"));
            OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
            RestaurantOrder.OrderStatus status = ordersDao.getOrderStatus(orderId);

            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.print(status.name());
            out.close();
        }catch(NumberFormatException nfe) {
            log.error("Could not get parameter 'id' from request");
            resp.setStatus(400);
        }
    }
}
