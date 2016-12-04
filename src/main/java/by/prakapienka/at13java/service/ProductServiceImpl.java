package by.prakapienka.at13java.service;

import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.util.JpaHibernateDaoFactory;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private OrderItemDao productDao = JpaHibernateDaoFactory.getOrderItemDao();

    @Override
    public List<OrderItem> getAll() {
        return productDao.getAll();
    }

    @Override
    public OrderItem get(int id) {
        return productDao.get(id);
    }

    @Override
    public OrderItem insert(OrderItem product) {
        return productDao.save(product);
    }

    @Override
    public OrderItem update(OrderItem product) {
        return productDao.save(product);
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }
}
