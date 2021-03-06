package com.idanch.servlets;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.FullOrder;
import com.idanch.data.util.JSONParseUtil;
import com.idanch.websocket.CheckNewOrdersSessionHandlerFactory;
import com.idanch.websocket.interfaces.BasicSessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/orderReceived.html")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"user", "admin"}))
public class OrderReceivedServlet extends HttpServlet {

	public static final Logger log = LoggerFactory.getLogger(OrderReceivedServlet.class);

	private static final BasicSessionHandler sessionHandler =
			CheckNewOrdersSessionHandlerFactory.getCheckNewOrdersSessionHandler();

	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
		String customer = request.getUserPrincipal().getName();
		Long orderId = ordersDao.newOrder(customer);
		if (orderId == null) {
			log.error("Could not get ${order_id}");
			return;
		}

		Map<String, String[]> params = request.getParameterMap();
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

		// send new order to client web sockets
		FullOrder order = ordersDao.getOrder(orderId);
		String orderJSON = JSONParseUtil.parseAddOrderJSON(order);
		sessionHandler.sendMessage(orderJSON);

		HttpSession session = request.getSession();
		session.setAttribute("orderId", orderId);
		response.sendRedirect("/thankYou");
	}
}
