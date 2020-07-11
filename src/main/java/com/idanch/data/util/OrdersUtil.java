package com.idanch.data.util;

import com.idanch.data.representations.RestaurantOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OrdersUtil {
    public static final Logger log = LoggerFactory.getLogger(OrdersUtil.class);

    public static String contentsToStr(RestaurantOrder order) {
        StringBuilder str = new StringBuilder();
        Map<Long,Integer> contents = order.getContents();
        for (Map.Entry<Long,Integer> entry: contents.entrySet()){
            str.append(entry.getKey());
            str.append(":");
            str.append(entry.getValue());
            str.append(",");
        }
        return str.toString();
    }

    public static void strToContents(String contentsStr, RestaurantOrder order) {
        if (contentsStr == null || contentsStr.equals("")) {
            return;
        }

        String[] orderItems = contentsStr.split(",");
        try{
            //last array item is empty (splitted string ends with ',')
            for (String orderItem : orderItems) {
                String[] orderPair = orderItem.split(":");
                long dishId = Long.parseLong(orderPair[0]);
                int quantity = Integer.parseInt(orderPair[1]);
                order.addToOrder(dishId, quantity);
            }
        }catch(Exception exception) {
            log.error("Failed to parse order contents field. Wrong format.");
        }
    }
}
