package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.dao.jpa.JpaOrderDao;
import by.prakapienka.at13java.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static by.prakapienka.at13java.TestData.*;

/**
 * Created by Restrictor on 21.11.2016.
 */
public class OrderDaoTest extends AbstractDaoTest {

    OrderDao orderDao;

    @Before
    public void setUp() {
        super.setUp();
        orderDao = new JpaOrderDao(em);
    }

    @Test
    public void testSave() {
        Order order = new Order("New order", USER2);
        Order persisted = orderDao.save(order, USER2_ID);
        Assert.assertEquals(order, persisted);
        Assert.assertTrue(orderDao.getAll(USER2_ID).size() == 3);
    }

    @Test
    public void testDelete() {
        orderDao.delete(ORDER4_ID, USER2_ID);
        Assert.assertTrue(orderDao.getAll(USER2_ID).size() == 1);
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
