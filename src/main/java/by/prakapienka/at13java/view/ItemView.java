package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

class ItemView implements View {

    private OrderDao orderDao = JpaHibernateDaoFactory.getOrderDao();

    @Override
    public ViewName show() {
        ConsoleHelper.writeMessage("\nOrderItem " +
                AppContext.getItem().getId() + ": " +
                AppContext.getItem().getName());
        ViewName viewName = ViewName.ORDER;

        ConsoleHelper.writeMessage("Choose operation.");
        ConsoleHelper.writeMessage("1. Delete item.");
        ConsoleHelper.writeMessage("2. Previous menu.");
        ConsoleHelper.writeMessage("0. Exit.");

        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = deleteOrderItem();
                break;
            case 2:
                viewName = ViewName.ORDER;
                break;
            case 0:
                viewName = ViewName.EXIT;
                break;
            default:
                ConsoleHelper.writeMessage("Unknown command.");
        }
        AppContext.setItem(null);

        return viewName;
    }

    private ViewName deleteOrderItem() {
        orderDao.deleteItem(
                AppContext.getActiveOrderId(),
                AppContext.getItem().getId(),
                AppContext.getActiveUserId()
        );
        return ViewName.ORDER;
    }
}
