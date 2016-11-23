package by.prakapienka.at13java;

import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;
import by.prakapienka.at13java.view.ViewName;
import by.prakapienka.at13java.view.ViewResolver;

import static by.prakapienka.at13java.util.ConsoleHelper.*;

public class DBManager {

    public static void main(String[] args) {

        ViewResolver.switchView(ViewName.LOGIN);

        ConsoleHelper.writeMessage("Exit.");
        JpaHibernateDaoFactory.close();

    }
}
