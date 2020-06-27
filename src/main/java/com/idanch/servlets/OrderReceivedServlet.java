package com.idanch.servlets;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class OrderReceivedServlet extends HttpServlet {

	public static final Logger log = LoggerFactory.getLogger(OrderReceivedServlet.class);

	public void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException {
		OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
		String customer = req.getUserPrincipal().getName();
		Long orderId = ordersDao.newOrder(customer);
		if (orderId == null) {
			log.error("Could not get ${order_id}");
			return;
		}

		Map<String, String[]> params = req.getParameterMap();
		for (Map.Entry<String,String[]> entry: params.entrySet()) {
			if (entry.getKey().startsWith("dish_")) {
				if (entry.getValue().length > 0) {
					try {
						long dishId = Long.parseLong(entry.getKey().substring(5));
						String quantityStr = entry.getValue()[0];
						if (quantityStr != null && !quantityStr.equals("")) {
							int quantity = Integer.parseInt(quantityStr);
							ordersDao.addToOrder(orderId, dishId, quantity);
						}
					}catch (Exception exception) {
						log.error(String.format("Failed to retrieve param {%s: %s}",
								entry.getKey(), Arrays.toString(entry.getValue())));
					}
				}
			}
		}

		HttpSession session = req.getSession();
		double total = ordersDao.calculateTotal(orderId);
		session.setAttribute("totalPrice", total);

		resp.sendRedirect("/thankYou.html");
	}
}
