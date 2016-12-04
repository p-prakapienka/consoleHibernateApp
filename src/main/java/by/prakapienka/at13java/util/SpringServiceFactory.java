package by.prakapienka.at13java.util;

import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.dao.UserDao;
import by.prakapienka.at13java.dao.jpa.JpaOrderDao;
import by.prakapienka.at13java.dao.jpa.JpaOrderItemDao;
import by.prakapienka.at13java.dao.jpa.JpaUserDao;
import by.prakapienka.at13java.service.OrderService;
import by.prakapienka.at13java.service.ProductService;
import by.prakapienka.at13java.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SpringServiceFactory {

    private static ClassPathXmlApplicationContext appCtx;

    private static UserService userService;
    private static OrderService orderService;
    private static ProductService productService;

    public static UserService getUserService() {
        return userService;
    }

    public static OrderService getOrderService() {
        return orderService;
    }

    public static ProductService getProductService() {
        return productService;
    }

    public static void init() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        userService = appCtx.getBean(UserService.class);
        orderService = appCtx.getBean(OrderService.class);
        productService = appCtx.getBean(ProductService.class);
    }

    public static void close() {
        appCtx.close();
    }

    private SpringServiceFactory() {}
}
