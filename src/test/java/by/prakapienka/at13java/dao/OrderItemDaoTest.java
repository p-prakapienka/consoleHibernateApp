package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.dao.jpa.JpaOrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static by.prakapienka.at13java.TestData.*;

/**
 * Created by Restrictor on 21.11.2016.
 */
public class OrderItemDaoTest extends AbstractDaoTest {

    OrderItemDao orderItemDao;

    @Before
    public void setUp() {
        super.setUp();
        orderItemDao = new JpaOrderItemDao(em);
    }

    @Test
    public void testSave() {
        OrderItem item = new OrderItem("New item", ORDER1);
        OrderItem persisted = orderItemDao.save(item, ORDER1_ID);
        Assert.assertEquals(item, persisted);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 3);
    }

    @Test
    public void testGet() {
        OrderItem item = orderItemDao.get(ORDER_ITEM_8_ID, ORDER4_ID);
        Assert.assertEquals(item, ORDER_ITEM_8);
    }

    @Test
    public void testDelete() {
        orderItemDao.delete(ORDER_ITEM_2_ID, ORDER1_ID);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 1);
    }

    @Test
    public void testGetAll() {
        orderItemDao.getAll(ORDER2_ID);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 2);
    }
}
