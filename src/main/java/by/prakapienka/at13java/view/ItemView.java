package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

class ItemView implements View {

    private OrderItemDao itemDao = JpaHibernateDaoFactory.getOrderItemDao();

    @Override
    public ViewName show() {
        ConsoleHelper.writeMessage("\nOrderItem " +
                AppContext.getItem().getId() + ": " +
                AppContext.getItem().getName());
        ViewName viewName = ViewName.ORDER;

        ConsoleHelper.writeMessage("Choose operation.");
        ConsoleHelper.writeMessage("1. Edit item.");
        ConsoleHelper.writeMessage("2. Delete item.");
        ConsoleHelper.writeMessage("3. Previous menu.");
        ConsoleHelper.writeMessage("0. Exit.");

        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = updateOrderItem();
                break;
            case 2:
                viewName = deleteOrderItem();
                break;
            case 3:
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

    private ViewName updateOrderItem() {
        while (true) {
            ConsoleHelper.writeMessage("Enter new name.");
            String newName = ConsoleHelper.readString();
            if (newName.length() > 50) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (newName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            AppContext.getItem().setName(newName);
            OrderItem updated = itemDao.save(
                    AppContext.getItem(),
                    AppContext.getActiveOrderId());
            if (updated != null) {
                ConsoleHelper.writeMessage("OrderItem successfully updated.");
            }
            break;
        }
        return ViewName.ORDER;
    }

    private ViewName deleteOrderItem() {
        itemDao.delete(AppContext.getItem().getId(), AppContext.getActiveOrderId());
        return ViewName.ORDER;
    }
}
