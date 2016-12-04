package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.service.OrderService;
import by.prakapienka.at13java.service.OrderServiceImpl;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.SpringServiceFactory;

class ItemView implements View {

    private OrderService orderService = SpringServiceFactory.getOrderService();

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
        orderService.deleteItem(
                AppContext.getActiveOrder(),
                AppContext.getItem().getId(),
                AppContext.getActiveUserId()
        );
        return ViewName.ORDER;
    }
}
