package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.OrderDao;
import by.prakapienka.at13java.model.Order;
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
            em.persist(order);
            em.getTransaction().commit();
            return order;
        } else {
            Order updated = em.merge(order);
            em.getTransaction().commit();
            return updated;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        em.getTransaction().begin();
        int result = em.createNamedQuery(Order.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate();
        em.getTransaction().commit();
        return result != 0;
    }

    @Override
    public Order get(int id, int userId) {
        Order order = em.find(Order.class, id);
        return order != null && order.getUser().getId() == userId ? order : null;
    }

    @Override
    public List<Order> getAll(int userId) {
        return em.createNamedQuery(Order.ALL, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
