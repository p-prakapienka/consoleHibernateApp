package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Restrictor on 21.11.2016.
 */
public class JpaOrderItemDao implements OrderItemDao {

    EntityManager em;

    public JpaOrderItemDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public OrderItem save(OrderItem item, int orderId) {
        if (!item.isNew() && get(item.getId(), orderId) == null) {
            return null;
        }
        em.getTransaction().begin();
        item.setOrder(em.getReference(Order.class, orderId));
        if (item.isNew()) {
            em.persist(item);
            em.getTransaction().commit();
            return item;
        } else {
            OrderItem updated = em.merge(item);
            em.getTransaction().commit();
            return updated;
        }
    }

    @Override
    public boolean delete(int id, int orderId) {
        em.getTransaction().begin();
        int result = em.createNamedQuery(OrderItem.DELETE)
                .setParameter("id", id)
                .setParameter("orderId", orderId)
                .executeUpdate();
        em.getTransaction().commit();
        return result != 0;
    }

    @Override
    public OrderItem get(int id, int orderId) {
        OrderItem item = em.find(OrderItem.class, id);
        return item != null && item.getOrder().getId() == orderId ? item : null;
    }

    @Override
    public List<OrderItem> getAll(int orderId) {
        return em.createNamedQuery(OrderItem.ALL, OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
