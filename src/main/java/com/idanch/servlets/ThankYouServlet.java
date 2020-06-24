package com.idanch.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ThankYouServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String totalPrice = req.getParameter("totalPrice");
        if (totalPrice != null) {

            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            out.println("<html><body><h1>Ricky's Restaurant</h1>");
            out.println("<h2>Order your food</h2>");

            out.println("Thank you - your order has been received. You need to pay $" + totalPrice);

            out.println("</body></html>");
            out.close();
        } else {
            resp.setStatus(403);
        }
    }
}
