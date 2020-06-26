package com.idanch.servlets;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.representations.Dish;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ListMenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");

        out.println("<head><title>Idan's Restaurant - Menu</title></head>");

        MenuDao menuDao = MenuDaoFactory.getMenuDao();
        List<Dish> menu = menuDao.getAllDishes();

        out.println("<html><body>");
        out.println("<h1>Welcome to Idan's Restaurant</h1>");
        out.println("<h2>please choose an order from the menu:</h2>");
        out.println("<form action='/orderReceived.html' method='post'>");
        out.println("<ul>");

        for (Dish dish: menu) {
            out.println("<li>" + dish.getName() + ": " + dish.getPriceShekels() +
                    " <input type='text' name='" + dish.getName() + " (" + dish.getPriceShekels() + " nis)'/>");
        }

        out.println("</ul>");

        out.println("<input type='submit' value='Place Order'/>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
