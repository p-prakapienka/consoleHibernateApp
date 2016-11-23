package by.prakapienka.at13java.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Restrictor on 22.11.2016.
 */
public class ViewResolver {

    private static Map<ViewName, View> viewMap;

    static {
        viewMap = new HashMap<>();
        viewMap.put(ViewName.LOGIN, new LoginView());
        viewMap.put(ViewName.USER, new UserView());
        viewMap.put(ViewName.ORDER, new OrderView());
        viewMap.put(ViewName.ITEM, new ItemView());
    }

    public static void switchView(ViewName viewName) {
        while (true) {
            if (viewName == ViewName.EXIT) {
                break;
            }
            viewName = viewMap.get(viewName).show();
        }
    }

    private ViewResolver() {}
}
