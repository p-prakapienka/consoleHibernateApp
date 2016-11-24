package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.model.Order;
import by.prakapienka.at13java.model.OrderItem;
import by.prakapienka.at13java.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaOrderDao implements OrderDao {

    private EntityManager em;

    public JpaOrderDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public Order save(Order order, int userId) {
        if (!order.isNew() && get(order.getId(), userId) == null) {
            return null;
        }
        em.getTransaction().begin();
        order.setUser(em.getReference(User.class, userId));
        if (order.isNew()) {
            try {
                em.persist(order);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                //TODO log exception
                return null;
            }
            return order;
        } else {
            Order updated = null;
            try {
                updated = em.merge(order);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                //TODO log exception
            }
            return updated;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        em.getTransaction().begin();
        int result = 0;

        try {
            result = em.createNamedQuery(Order.DELETE)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //TODO log exception
        }
        return result != 0;
    }

    @Override
    public Order get(int id, int userId) {
        Order order = em.find(Order.class, id);
        return order != null && order.getUser().getId() == userId ? order : null;
    }

    @Override
    public Order getWithItems(int id, int userId) {
        return em.createNamedQuery(Order.GET_WITH_ITEMS, Order.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public List<Order> getAll(int userId) {
        return em.createNamedQuery(Order.ALL, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Order insertItem(int id, int itemId, int userId) {
        Order updated = null;
        try {
            em.getTransaction().begin();
            Order order = getWithItems(id, userId);
            OrderItem item = em.getReference(OrderItem.class, itemId);
            order.getOrderItems().add(item);
            updated = em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //TODO log exception
        }
        return updated;
    }

    @Override
    public Order deleteItem(int id, int itemId, int userId) {
        Order updated = null;
        try {
            em.getTransaction().begin();
            Order order = getWithItems(id, userId);
            OrderItem item = em.getReference(OrderItem.class, itemId);
            order.getOrderItems().remove(item);
            updated = em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //TODO log exception
        }
        return updated;
    }
}
