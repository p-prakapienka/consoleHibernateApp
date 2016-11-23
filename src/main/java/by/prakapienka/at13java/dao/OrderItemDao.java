package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.model.OrderItem;

import java.util.List;

public interface OrderItemDao {

    OrderItem save(OrderItem item, int orderId);

    boolean delete(int id, int orderId);

    OrderItem get(int id, int orderId);

    List<OrderItem> getAll(int orderId);

}
