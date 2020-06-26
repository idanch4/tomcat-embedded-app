package com.idanch.servlets;

import com.idanch.data.IdansRestaurant;
import com.idanch.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderReceivedServlet extends HttpServlet {

	public static final Logger log = LoggerFactory.getLogger(OrderReceivedServlet.class);

	public void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
		RestaurantOrder order = IdansRestaurant.newOrder();
		for (String dishName: IdansRestaurant.getMenu().getAllDishNames()) {
			String quantity = req.getParameter(dishName);
			if (quantity != null && !quantity.equals("")) {
				try {
					int quantityInt = Integer.parseInt(quantity);
					order.addToOrder(dishName, quantityInt);
				}catch (NumberFormatException nfe) {
					log.error("An order quantity parameter of " + dishName + " was null - thus ignored");
				}
			}
		}

		HttpSession session = req.getSession();
		session.setAttribute("totalPrice", order.calculateOrderPrice());

		resp.sendRedirect("/thankYou.html");
	}
}
