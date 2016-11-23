package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.model.Order;

import java.util.List;

public interface OrderDao {

    Order save(Order order, int userId);

    boolean delete(int id, int userId);

    Order get(int id, int userId);

    List<Order> getAll(int userId);

}
