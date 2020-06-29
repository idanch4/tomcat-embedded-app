package com.idanch.servlets;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/thankYou.html")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"user", "admin"}))
public class ThankYouServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Double totalPrice = (Double) req.getSession().getAttribute("totalPrice");
        if (totalPrice != null) {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html; charset=utf-8");

            out.println("<html><body><h1>Ricky's Restaurant</h1>");
            out.println("<h2>Order your food</h2>");

            out.println("Thank you - your order has been received. You need to pay " + totalPrice + " SS\u20AA");

            out.println("</body></html>");
            out.close();
        } else {
            resp.setStatus(403);
        }
    }
}
