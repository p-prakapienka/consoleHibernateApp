package by.prakapienka.at13java.view;

import by.prakapienka.at13java.AppContext;
import by.prakapienka.at13java.dao.UserDao;
import by.prakapienka.at13java.model.User;
import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

import java.util.List;

class LoginView implements View {

    private UserDao userDao = JpaHibernateDaoFactory.getUserDao();

    public ViewName show() {
        ConsoleHelper.writeMessage("\nChoose operation.");
        ViewName viewName = ViewName.LOGIN;

        ConsoleHelper.writeMessage("1. Choose user.");
        ConsoleHelper.writeMessage("2. Create user.");
        ConsoleHelper.writeMessage("0. Exit.");
        int command = ConsoleHelper.readNumber();
        switch (command) {
            case 1:
                viewName = showAllUsers();
                break;
            case 2:
                viewName = createUser();
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
        List<User> users = userDao.getAll();
        ConsoleHelper.writeMessage("\nAvailable users:");

        if (users.isEmpty()) {
            ConsoleHelper.writeMessage("No users found.");
        }
        for (User user : users) { //TODO refactor user.toString
            ConsoleHelper.writeMessage("User " + user.getId() + ": " + user.getName());
        }
        ConsoleHelper.writeMessage("Enter user id or 0 to go back.");
        int result = ConsoleHelper.readNumber();

        if (result == 0) {
            return ViewName.LOGIN;
        } else if (result == -1) {
            ConsoleHelper.writeMessage("Unknown command.");
            return ViewName.LOGIN;
        } else {
            User user = userDao.get(result);
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
            User user = userDao.save(new User(userName));
            if (user != null) {
                AppContext.setActiveUser(user);
                ConsoleHelper.writeMessage("User successfully added.");
            }
            break;
        }
        return ViewName.USER;
    }
}
