package by.prakapienka.at13java.view;

import java.util.HashMap;
import java.util.Map;

public class ViewResolver {

    private static Map<ViewName, View> viewMap;

    static {
        viewMap = new HashMap<>();
        viewMap.put(ViewName.LOGIN, new LoginView());
        viewMap.put(ViewName.USER, new UserView());
        viewMap.put(ViewName.ORDER, new OrderView());
        viewMap.put(ViewName.ITEM, new ItemView());
        viewMap.put(ViewName.PRODUCT, new ProductView());
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
