package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.dao.jpa.JpaOrderDao;
import by.prakapienka.at13java.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static by.prakapienka.at13java.TestData.*;

public class OrderDaoTest extends AbstractDaoTest {

    private OrderDao orderDao;

    @Before
    public void setUp() {
        super.setUp();
        orderDao = new JpaOrderDao(em);
    }

    @Test
    public void testInsertAndDelete() {
        Order order = new Order("New order", USER2);
        Order persisted = orderDao.save(order, USER2_ID);
        Assert.assertEquals(order, persisted);
        Assert.assertTrue(orderDao.getAll(USER2_ID).size() == 3);
        orderDao.delete(persisted.getId(), USER2_ID);
        Assert.assertTrue(orderDao.getAll(USER2_ID).size() == 2);
    }

    @Test
    public void update() {
        Order order = new Order(ORDER3_ID, "New name", USER2);
        Order updated = orderDao.save(order, USER2_ID);
        Assert.assertEquals(updated, order);
        order.setName(ORDER3.getName());
        order = orderDao.save(order, USER2_ID);
        Assert.assertEquals(ORDER3, order);
    }

    @Test
    public void testGet() {
        Order order = orderDao.get(ORDER3_ID, USER2_ID);
        Assert.assertEquals(order, ORDER3);
    }

    @Test
    public void testGetAll() {
        Assert.assertTrue(orderDao.getAll(USER1_ID).size() == 2);
    }
}
