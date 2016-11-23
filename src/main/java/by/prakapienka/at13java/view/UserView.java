package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.dao.UserDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.User;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

import java.util.List;

class UserView implements View {

    private UserDao userDao = JpaHibernateDaoFactory.getUserDao();
    private OrderDao orderDao = JpaHibernateDaoFactory.getOrderDao();

    @Override
    public ViewName show() {
        ConsoleHelper.writeMessage("\nUser Menu. User " +
                AppContext.getActiveUser().getId() + ": " +
                AppContext.getActiveUser().getName());
        ViewName viewName = ViewName.USER;

        ConsoleHelper.writeMessage("Choose operation.");
        ConsoleHelper.writeMessage("1. Show orders.");
        ConsoleHelper.writeMessage("2. Create new order.");
        ConsoleHelper.writeMessage("3. Edit user.");
        ConsoleHelper.writeMessage("4. Delete user.");
        ConsoleHelper.writeMessage("5. Previous menu.");
        ConsoleHelper.writeMessage("0. Exit.");

        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = showAllOrders();
                break;
            case 2:
                viewName = createOrder();
                break;
            case 3:
                viewName = updateUser();
                break;
            case 4:
                viewName = deleteUser();
                break;
            case 5:
                AppContext.setActiveUser(null);
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

    private ViewName showAllOrders() {
        List<Order> orders = orderDao.getAll(AppContext.getActiveUserId());
        ConsoleHelper.writeMessage("\nUser orders:");

        if (orders.isEmpty()) {
            ConsoleHelper.writeMessage("No orders found.");
        }
        for (Order order : orders) { //TODO refactor order.toString
            ConsoleHelper.writeMessage("Order " + order.getId() + ": " + order.getName());
        }
        ConsoleHelper.writeMessage("Enter order id or 0 to go back.");
        int result = ConsoleHelper.readNumber();
        if (result == 0) {
            return ViewName.USER;
        } else if (result == -1) {
            ConsoleHelper.writeMessage("Unknown command.");
            return ViewName.USER;
        } else {
            Order order = orderDao.get(result, AppContext.getActiveUserId());
            if (order != null) {
                AppContext.setActiveOrder(order);
                return ViewName.ORDER;
            } else {
                return ViewName.USER;
            }
        }
    }

    private ViewName createOrder() {
        while (true) {
            ConsoleHelper.writeMessage("Enter order name.");
            String orderName = ConsoleHelper.readString();
            if (orderName.length() > 50) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (orderName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            Order order = orderDao.save(
                    new Order(orderName),
                    AppContext.getActiveUserId());
            if (order != null) {
                AppContext.setActiveOrder(order);
                ConsoleHelper.writeMessage("Order successfully created.");
            }
            break;
        }
        return ViewName.ORDER;
    }

    private ViewName updateUser() {
        while (true) {
            ConsoleHelper.writeMessage("Enter new user name.");
            String newName = ConsoleHelper.readString();
            if (newName.length() > 30) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (newName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            AppContext.getActiveUser().setName(newName);
            User updated = userDao.save(AppContext.getActiveUser());
            if (updated != null) {
                ConsoleHelper.writeMessage("User successfully updated.");
            }
            break;
        }
        return ViewName.USER;
    }

    private ViewName deleteUser() {
        userDao.delete(AppContext.getActiveUserId());
        AppContext.setActiveUser(null);
        return ViewName.LOGIN;
    }
}
