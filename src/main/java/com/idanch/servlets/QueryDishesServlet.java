package com.idanch.servlets;

import com.idanch.data.Dish;
import com.idanch.data.IdansRestaurant;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class QueryDishesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String query = req.getParameter("searchQuery");
        if (query == null) {
            query = "";
        }

        List<Dish> queryResult = IdansRestaurant.getMenu().findDishesByName(query);
        Writer writer = resp.getWriter();

        if (queryResult.size() == 0) {
            writer.write("No dishes were found.");
            return;
        }

        writer.write("<p>Search Results:</p>");
        for (Dish dish: queryResult) {
            writer.write("<p>");
            writer.write(dish.getName());
            if (dish.getDescription() != null && !dish.getDescription().equals("")) {
                writer.write(": " + dish.getDescription());
            }
            writer.write("</p>");
        }
        writer.flush();
    }
}