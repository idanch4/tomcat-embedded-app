package com.idanch.servlets;

import com.idanch.data.factories.MenuDaoFactory;
import com.idanch.data.interfaces.MenuDao;
import com.idanch.data.representations.Dish;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchResults")
public class QueryDishesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchQuery = request.getParameter("searchQuery");
        MenuDao menuDao = MenuDaoFactory.getMenuDao();
        List<Dish> dishes = menuDao.findDishes(searchQuery);

        request.setAttribute("dishes", dishes);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/searchResults.jsp");
        dispatcher.forward(request, response);
    }
}