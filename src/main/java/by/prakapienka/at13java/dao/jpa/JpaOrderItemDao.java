package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaOrderItemDao implements OrderItemDao {

    private EntityManager em;

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
            try {
                em.persist(item);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                //TODO log exception
                return null;
            }
            return item;
        } else {
            OrderItem updated = null;
            try {
                updated = em.merge(item);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                //TODO log exception
            }
            return updated;
        }
    }

    @Override
    public boolean delete(int id, int orderId) {
        em.getTransaction().begin();
        int result = 0;

        try {
            result = em.createNamedQuery(OrderItem.DELETE)
                    .setParameter("id", id)
                    .setParameter("orderId", orderId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //TODO log exception
        }
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
