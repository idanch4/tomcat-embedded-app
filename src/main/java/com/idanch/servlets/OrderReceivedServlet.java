package com.idanch.servlets;

import com.idanch.data.IdansRestaurant;
import com.idanch.data.RestaurantOrder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderReceivedServlet extends HttpServlet {
	
	public void service (HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/html");

		RestaurantOrder order = IdansRestaurant.newOrder();
		for (String dishName: IdansRestaurant.getMenu().getAllDishNames()) {
			String quantity = req.getParameter(dishName);
			if (quantity != null) {
				try {
					int quantityInt = Integer.parseInt(quantity);
					order.addToOrder(dishName, quantityInt);
				}catch (NumberFormatException nfe) {
					//TODO:: log this
				}
			}
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<html><body><h1>Ricky's Restaurant</h1>");
		out.println("<h2>Order your food</h2>");
		
		out.println("Thank you - your order has been received. You need to pay $" +
				order.calculateOrderPrice());
				
		out.println("</body></html>");
		out.close();
	}
}
