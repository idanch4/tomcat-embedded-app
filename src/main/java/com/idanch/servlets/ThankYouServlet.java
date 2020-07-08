package com.idanch.servlets;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.RestaurantOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/thankYou")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"user", "admin"}))
public class ThankYouServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long orderId = (Long) request.getSession().getAttribute("orderId");
        if (orderId == null) {
            response.sendRedirect("/listMenu");
            return;
        }

        OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
        RestaurantOrder.OrderStatus orderStatus = ordersDao.getOrderStatus(orderId);
        Double totalPrice = ordersDao.calculateTotal(orderId);

        request.setAttribute("status", orderStatus);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("id", orderId);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/thankYou.jsp");
        dispatcher.forward(request, response);
    }
}