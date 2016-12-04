package by.prakapienka.at13java;

import by.prakapienka.at13java.util.ConsoleHelper;
import by.prakapienka.at13java.util.SpringServiceFactory;
import by.prakapienka.at13java.view.ViewName;
import by.prakapienka.at13java.view.ViewResolver;

public class DBManager {

    public static void main(String[] args) {

        SpringServiceFactory.init();

        ViewResolver.switchView(ViewName.LOGIN);

        ConsoleHelper.writeMessage("Exit.");

        SpringServiceFactory.close();

    }
}
