package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

public class ProductView implements View {

    private OrderItemDao itemDao = JpaHibernateDaoFactory.getOrderItemDao();

    @Override
    public ViewName show() {
        ConsoleHelper.writeMessage("\nProduct Menu. " + AppContext.getActiveProduct());
        ViewName viewName = ViewName.PRODUCT;

        ConsoleHelper.writeMessage("Choose operation.");;
        ConsoleHelper.writeMessage("1. Edit product.");
        ConsoleHelper.writeMessage("2. Delete product.");
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
                AppContext.setActiveOrder(null);
                viewName = ViewName.LOGIN;
                break;
            case 0:
                viewName = ViewName.EXIT;
                break;
            default:
                ConsoleHelper.writeMessage("Unknown command.");
        }

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
            AppContext.getActiveProduct().setName(newName);
            OrderItem updated = itemDao.save(AppContext.getActiveProduct());
            if (updated != null) {
                ConsoleHelper.writeMessage("Product successfully updated.");
            }
            break;
        }
        return ViewName.PRODUCT;
    }

    private ViewName deleteOrderItem() {
        itemDao.delete(AppContext.getActiveProduct().getId());
        AppContext.setActiveProduct(null);
        return ViewName.LOGIN;
    }
}
