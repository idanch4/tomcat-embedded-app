package com.idanch.servlets;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.representations.Dish;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("")
public class QueryDishesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String query = req.getParameter("searchQuery");
        if (query == null) {
            query = "";
        }

        MenuDao menuDao = MenuDaoFactory.getMenuDao();
        List<Dish> queryResult = menuDao.findDishes(query);
                //IdansRestaurant.getMenu().findDishesByName(query);
        Writer writer = resp.getWriter();

        if (queryResult.size() == 0) {
            writer.write("No dishes were found.");
            return;
        }

        writer.write("<p>Menu:</p>");
        for (Dish dish: queryResult) {
            writer.write("<h3>");
            writer.write(dish.getName() + " (" + dish.getPriceShekels() +" nis)");
            if (dish.getDescription() != null && !dish.getDescription().equals("")) {
                writer.write(": " + dish.getDescription());
            }
            writer.write("</h3>");
        }
        if (req.getUserPrincipal() == null || req.getUserPrincipal().getName().equals("")) {
            writer.write("<h2>Please <a href='/listMenu.html'>login</a> to order.</h2>");
        } else {
            writer.write("<h2><a href='/logout.html'>Logout</a></h2>");
        }

        writer.flush();
    }
}