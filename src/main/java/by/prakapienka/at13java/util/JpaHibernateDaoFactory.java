package by.prakapienka.at13java.util;

import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.dao.UserDao;
import by.prakapienka.at13java.dao.jpa.JpaOrderDao;
import by.prakapienka.at13java.dao.jpa.JpaOrderItemDao;
import by.prakapienka.at13java.dao.jpa.JpaUserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Restrictor on 22.11.2016.
 */
public class JpaHibernateDaoFactory {

    private static final EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("USERORDERS");
    private static final EntityManager em = emf.createEntityManager();

    public static EntityManager getEntityManager() {
        return em;
    }

    private static  final UserDao userDao = new JpaUserDao(em);
    private static  final OrderDao orderDao = new JpaOrderDao(em);
    private static  final OrderItemDao itemDao = new JpaOrderItemDao(em);

    public static UserDao getUserDao() {
        return userDao;
    }

    public static OrderDao getOrderDao() {
        return orderDao;
    }

    public static OrderItemDao getOrderItemDao() {
        return itemDao;
    }

    public static void close() {
        em.close();
        emf.close();
    }

    private JpaHibernateDaoFactory() {}
}
