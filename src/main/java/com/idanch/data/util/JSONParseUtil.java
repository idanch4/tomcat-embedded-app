package com.idanch.data.util;

import com.idanch.data.factories.OrdersDaoFactory;
import com.idanch.data.interfaces.OrdersDao;
import com.idanch.data.representations.Dish;
import com.idanch.data.representations.FullOrder;
import com.idanch.data.representations.RestaurantOrder;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParseUtil {
    public static String parseAddOrderJSON(FullOrder order) {
        JSONObject resultJSON = new JSONObject();
        resultJSON.put("action", "add");
        resultJSON.put("order",parseOrderJSON(order));

        return resultJSON.toString();
    }

    public static String parseRemoveOrderJSON(FullOrder order) {
        JSONObject resultJSON = new JSONObject();
        resultJSON.put("action", "remove");
        resultJSON.put("order",parseOrderJSON(order));

        return resultJSON.toString();
    }

    public static String parseOrderJSON(FullOrder order) {
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("id", order.getId());
        orderJSON.put("customer", order.getCustomer());
        orderJSON.put("status", order.getStatus());
        JSONArray contentsJSONArr = new JSONArray();
        for (Dish dish: order.getContents().keySet()) {
            JSONObject dishJSON = new JSONObject();
            dishJSON.put("id", dish.getId());
            dishJSON.put("name", dish.getName());
            dishJSON.put("description", dish.getDescription());
            dishJSON.put("category", dish.getCategory());
            dishJSON.put("priceShekels", dish.getPriceShekels());

            contentsJSONArr.put(dishJSON);
        }

        orderJSON.put("contents", contentsJSONArr);

        OrdersDao ordersDao = OrdersDaoFactory.getOrdersDao();
        double totalPrice = ordersDao.calculateTotal(order.getId());
        orderJSON.put("totalPrice", totalPrice);

        return orderJSON.toString();
    }
}
