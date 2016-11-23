package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

import java.util.List;

class OrderView implements View {

    private OrderDao orderDao = JpaHibernateDaoFactory.getOrderDao();
    private OrderItemDao orderItemDao = JpaHibernateDaoFactory.getOrderItemDao();

    @Override
    public ViewName show() {
        ConsoleHelper.writeMessage("\nOrder Menu. User " +
                AppContext.getActiveUser().getId() + ": " +
                AppContext.getActiveUser().getName() +  ". Order " +
                AppContext.getActiveOrder().getId() + ": " +
                AppContext.getActiveOrder().getName());
        ViewName viewName = ViewName.ORDER;

        ConsoleHelper.writeMessage("Choose operation.");
        ConsoleHelper.writeMessage("1. Show items.");
        ConsoleHelper.writeMessage("2. Create new item.");
        ConsoleHelper.writeMessage("3. Edit order name.");
        ConsoleHelper.writeMessage("4. Delete order.");
        ConsoleHelper.writeMessage("5. Previous menu.");
        ConsoleHelper.writeMessage("0. Exit.");

        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = showAllOrderItems();
                break;
            case 2:
                viewName = createOrderItem();
                break;
            case 3:
                viewName = updateOrder();
                break;
            case 4:
                viewName = deleteOrder();
                break;
            case 5:
                AppContext.setActiveOrder(null);
                viewName = ViewName.USER;
                break;
            case 0:
                viewName = ViewName.EXIT;
                break;
            default:
                ConsoleHelper.writeMessage("Unknown command.");
        }

        return viewName;
    }

    private ViewName showAllOrderItems() {
        List<OrderItem> items = orderItemDao.getAll(AppContext.getActiveOrderId());
        ConsoleHelper.writeMessage("\nOrder " + AppContext.getActiveOrder() + ":");

        if (items.isEmpty()) {
            ConsoleHelper.writeMessage("No items found.");
        }
        for (OrderItem item : items) {
            ConsoleHelper.writeMessage("Item " + item.getId() + ": " + item.getName());
        }
        ConsoleHelper.writeMessage("Enter item id or 0 to go back.");
        int result = ConsoleHelper.readNumber();
        if (result == 0) {
            return ViewName.ORDER;
        } else if (result == -1) {
            ConsoleHelper.writeMessage("Unknown command.");
            return ViewName.ORDER;
        } else {
            OrderItem item = orderItemDao.get(result, AppContext.getActiveOrderId());
            if (item != null) {
                AppContext.setItem(item);
                return ViewName.ITEM;
            } else {
                return ViewName.ORDER;
            }
        }
    }

    private ViewName createOrderItem() {
        while (true) {
            ConsoleHelper.writeMessage("Enter item name.");
            String itemName = ConsoleHelper.readString();
            if (itemName.length() > 50) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (itemName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            OrderItem item = orderItemDao.save(
                    new OrderItem(itemName),
                    AppContext.getActiveOrderId());
            if (item != null) {
                ConsoleHelper.writeMessage("Item successfully created.");
            }
            break;
        }
        return ViewName.ORDER;
    }

    private ViewName updateOrder() {
        while (true) {
            ConsoleHelper.writeMessage("Enter new order name.");
            String newName = ConsoleHelper.readString();
            if (newName.length() > 50) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (newName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            AppContext.getActiveOrder().setName(newName);
            Order updated = orderDao.save(
                    AppContext.getActiveOrder(),
                    AppContext.getActiveUserId());
            if (updated != null) {
                ConsoleHelper.writeMessage("Order successfully updated.");
            }
            break;
        }
        return ViewName.ORDER;
    }

    private ViewName deleteOrder() {
        orderDao.delete(AppContext.getActiveOrderId(), AppContext.getActiveUserId());
        AppContext.setActiveOrder(null);
        return ViewName.USER;
    }
}
