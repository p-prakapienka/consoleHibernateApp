package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.dao.jpa.JpaOrderItemDao;
import by.prakapienka.at13java.model.OrderItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static by.prakapienka.at13java.TestData.*;

public class OrderItemDaoTest extends AbstractDaoTest {

    private OrderItemDao orderItemDao;

    @Before
    public void setUp() {
        super.setUp();
        orderItemDao = new JpaOrderItemDao(em);
    }

    @Test
    public void testInsertAndDelete() {
        OrderItem item = new OrderItem("New item");
        OrderItem persisted = orderItemDao.save(item, ORDER1_ID);
        Assert.assertEquals(item, persisted);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 9);
        orderItemDao.delete(persisted.getId(), ORDER1_ID);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 8);
    }

    @Test
    public void testUpdate() {
        OrderItem item = new OrderItem(ORDER_ITEM_7_ID, "New name");
        OrderItem updated = orderItemDao.save(item, ORDER4_ID);
        Assert.assertEquals(updated, item);
        item.setName(ORDER_ITEM_7.getName());
        item = orderItemDao.save(item, ORDER4_ID);
        Assert.assertEquals(ORDER_ITEM_7, item);
    }

    @Test
    public void testGet() {
        OrderItem item = orderItemDao.get(ORDER_ITEM_8_ID, ORDER4_ID);
        Assert.assertEquals(item, ORDER_ITEM_8);
    }

    @Test
    public void testGetAll() {
        orderItemDao.getAll(ORDER2_ID);
        Assert.assertTrue(orderItemDao.getAll(ORDER1_ID).size() == 8);
    }
}
