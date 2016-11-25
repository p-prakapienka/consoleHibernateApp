package by.prakapienka.at13java.dao;

import by.prakapienka.at13java.dao.jpa.JpaOrderDao;
import by.prakapienka.at13java.dao.jpa.JpaOrderItemDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static by.prakapienka.at13java.TestData.*;

public class OrderDaoTest extends AbstractDaoTest {

    private OrderDao orderDao;
    private OrderItemDao orderItemDao;

    @Before
    public void setUp() {
        super.setUp();
        orderDao = new JpaOrderDao(em);
        orderItemDao = new JpaOrderItemDao(em);
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
    public void testGetWithItems() {
        Order order = orderDao.getWithItems(ORDER2_ID, USER1_ID);
        Assert.assertEquals(order, ORDER2);
        Assert.assertTrue(order.getOrderItems().size() == 2);
    }

    @Test
    public void testGetAll() {
        Assert.assertTrue(orderDao.getAll(USER1_ID).size() == 2);
    }

    @Test
    public void testInsertAndDeleteItem() {
        OrderItem item = orderItemDao.save(new OrderItem("New Item"));
        Order order = orderDao.insertItem(ORDER4_ID, item.getId(), USER2_ID);
        Assert.assertTrue(order.getOrderItems().size() == 3);
        order = orderDao.deleteItem(ORDER4_ID, item.getId(), USER2_ID);
        Assert.assertTrue(order.getOrderItems().size() == 2);
        orderItemDao.delete(item.getId());
    }

    @Test
    public void testInsertItemInNewOrder() {
        Order order = new Order("New Order");
        order = orderDao.save(order, USER1_ID);
        order = orderDao.insertItem(order.getId(), ORDER_ITEM_3_ID, USER1_ID);
        Assert.assertNotNull(order);
        order = orderDao.deleteItem(order.getId(), ORDER_ITEM_3_ID, USER1_ID);
        Assert.assertTrue(order.getOrderItems().size() == 0);
        Assert.assertTrue(orderDao.delete(order.getId(), USER1_ID));
    }

    @Test
    public void testGetWithItemsEmptySet() {
        Order order = new Order("New Order");
        order = orderDao.save(order, USER1_ID);
        order = orderDao.getWithItems(order.getId(), USER1_ID);
        Assert.assertTrue(order.getOrderItems().isEmpty());
        orderDao.delete(order.getId(), USER1_ID);
        Assert.assertTrue(orderDao.getAll(USER1_ID).size() == 2);
    }
}
