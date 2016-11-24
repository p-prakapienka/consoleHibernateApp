package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.OrderItemDao;
import by.prakapienka.at13java.model.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaOrderItemDao implements OrderItemDao {

    private EntityManager em;

    public JpaOrderItemDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public OrderItem save(OrderItem item) {
        em.getTransaction().begin();
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
    public boolean delete(int id) {
        em.getTransaction().begin();
        int result = 0;

        try {
            result = em.createNamedQuery(OrderItem.DELETE)
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            //TODO log exception
        }
        return result != 0;
    }

    @Override
    public OrderItem get(int id) {
        return em.find(OrderItem.class, id);
    }

    @Override
    public List<OrderItem> getAll() {
        return em.createNamedQuery(OrderItem.ALL, OrderItem.class)
                .getResultList();
    }
}
