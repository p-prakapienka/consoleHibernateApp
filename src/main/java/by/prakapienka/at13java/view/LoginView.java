package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.model.User;
import by.prakapienka.at13java.service.ProductService;
import by.prakapienka.at13java.service.UserService;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.SpringServiceFactory;

import java.util.List;

class LoginView implements View {

    private UserService userService = SpringServiceFactory.getUserService();
    private ProductService productService = SpringServiceFactory.getProductService();

    public ViewName show() {
        ConsoleHelper.writeMessage("\nChoose operation.");
        ViewName viewName = ViewName.LOGIN;

        ConsoleHelper.writeMessage("1. Choose user.");
        ConsoleHelper.writeMessage("2. Create user.");
        ConsoleHelper.writeMessage("3. Choose product.");
        ConsoleHelper.writeMessage("4. Create product.");
        ConsoleHelper.writeMessage("0. Exit.");
        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = showAllUsers();
                break;
            case 2:
                viewName = createUser();
                break;
            case 3:
                viewName = showAllProducts();
                break;
            case 4:
                viewName = createProduct();
                break;
            case 0:
                viewName = ViewName.EXIT;
                break;
            default:
                ConsoleHelper.writeMessage("Unknown command.");
        }

        return viewName;
    }

    private ViewName showAllUsers() {
        List<User> users = userService.getAll();
        ConsoleHelper.writeMessage("\nAvailable users:");

        if (users.isEmpty()) {
            ConsoleHelper.writeMessage("No users found.");
        }
        for (User user : users) {
            ConsoleHelper.writeMessage(user.toString());
        }
        ConsoleHelper.writeMessage("Enter user id or 0 to go back.");
        int result = ConsoleHelper.readNumber();

        if (result == 0) {
            return ViewName.LOGIN;
        } else if (result == -1) {
            ConsoleHelper.writeMessage("Unknown command.");
            return ViewName.LOGIN;
        } else {
            User user = userService.get(result);
            if (user != null) {
                AppContext.setActiveUser(user);
                return ViewName.USER;
            } else {
                ConsoleHelper.writeMessage("User not found.");
                return ViewName.LOGIN;
            }
        }
    }

    private ViewName createUser() {
        while (true) {
            ConsoleHelper.writeMessage("\nEnter user name.");
            String userName = ConsoleHelper.readString();
            if (userName.length() > 30) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (userName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            User user = userService.insert(new User(userName));
            if (user != null) {
                AppContext.setActiveUser(user);
                ConsoleHelper.writeMessage("User successfully added.");
            }
            break;
        }
        return ViewName.USER;
    }

    private ViewName showAllProducts() {
        List<OrderItem> products = productService.getAll();
        ConsoleHelper.writeMessage("\nAvailable products:");

        if (products.isEmpty()) {
            ConsoleHelper.writeMessage("No products found.");
        }
        for (OrderItem product : products) {
            ConsoleHelper.writeMessage(product.toString());
        }
        ConsoleHelper.writeMessage("Enter product id or 0 to go back.");
        int result = ConsoleHelper.readNumber();

        if (result == 0) {
            return ViewName.LOGIN;
        } else if (result == -1) {
            ConsoleHelper.writeMessage("Unknown command.");
            return ViewName.LOGIN;
        } else {
            OrderItem product = productService.get(result);
            if (product != null) {
                AppContext.setActiveProduct(product);
                return ViewName.PRODUCT;
            } else {
                ConsoleHelper.writeMessage("Product not found.");
                return ViewName.LOGIN;
            }
        }
    }

    private ViewName createProduct() {
        while (true) {
            ConsoleHelper.writeMessage("Enter product name.");
            String itemName = ConsoleHelper.readString();
            if (itemName.length() > 50) {
                ConsoleHelper.writeMessage("Name is too long.");
                continue;
            }
            if (itemName.length() == 0) {
                ConsoleHelper.writeMessage("Name is too short.");
                continue;
            }
            OrderItem product = productService.insert(new OrderItem(itemName));
            if (product != null) {
                AppContext.setActiveProduct(product);
                ConsoleHelper.writeMessage("Product successfully created.");
            }
            break;
        }
        return ViewName.PRODUCT;
    }
}
