package by.prakapienka.at13java;

import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.model.User;

/**
 * Created by Restrictor on 22.11.2016.
 */
public class AppContext {

    private static User activeUser;

    private static Order order;

    private static OrderItem item;

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        if (activeUser == null) {
            setActiveOrder(null);
        }
        AppContext.activeUser = activeUser;
    }

    public static Integer getActiveUserId() {
        return activeUser.getId();
    }

    public static void setActiveOrder(Order order) {
        if (order == null) {
            setItem(null);
        }
        AppContext.order = order;
    }

    public static Order getActiveOrder() {
        return order;
    }

    public static Integer getActiveOrderId() {
        return order.getId();
    }

    public static OrderItem getItem() {
        return item;
    }

    public static void setItem(OrderItem item) {
        AppContext.item = item;
    }

    private AppContext() {}
}
