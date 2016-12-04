package by.prakapienka.at13java.service;


import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = JpaHibernateDaoFactory.getOrderDao();

    @Override
    public Order get(int id, int userId) {
        return orderDao.get(id, userId);
    }

    @Override
    public void delete(int id, int userId) {
        orderDao.delete(id, userId);
    }

    @Override
    public List<Order> getAll(int userId) {
        return orderDao.getAll(userId);
    }

    @Override
    public Order getWithItems(int id, int userId) {
        return orderDao.getWithItems(id, userId);
    }

    @Override
    public Order insert(Order order, int userId) {
        return orderDao.save(order, userId);
    }

    @Override
    public Order update(Order order, int userId) {
        return orderDao.save(order, userId);
    }

    @Override
    public Order insertItem(Order order, int itemId, int userId) {
        return orderDao.insertItem(order.getId(), itemId, userId);
    }

    @Override
    public Order deleteItem(Order order, int itemId, int userId) {
        return orderDao.deleteItem(order.getId(), itemId, userId);
    }
}