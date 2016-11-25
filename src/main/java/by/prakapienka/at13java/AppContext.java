package by.prakapienka.at13java;

import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.model.User;

public class AppContext {

    private static User activeUser;

    private static Order order;

    private static OrderItem item;

    private static OrderItem product;

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

    public static OrderItem getActiveProduct() {
        return product;
    }

    public static void setActiveProduct(OrderItem product) {
        AppContext.product = product;
    }

    private AppContext() {}
}
