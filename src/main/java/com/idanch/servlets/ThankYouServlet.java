package com.idanch.servlets;

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
        Double totalPrice = (Double) request.getSession().getAttribute("totalPrice");
        if (totalPrice == null) {
            response.sendRedirect("/listMenu");
            return;
        }

        request.setAttribute("totalPrice", totalPrice);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/thankYou.jsp");
        dispatcher.forward(request, response);
    }
}