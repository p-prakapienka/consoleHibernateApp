package by.prakapienka.at13java.dao.jpa;

import by.prakapienka.at13java.dao.UserDao;
import by.prakapienka.at13java.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaUserDao implements UserDao {

    private EntityManager em;

    public JpaUserDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.getTransaction().begin();
        if (user.isNew()) {
            try {
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                //TODO log exception
                return null;
            }
            return user;
        } else {
            User updated = null;
            try {
                updated = em.merge(user);
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
            result = em.createNamedQuery(User.DELETE)
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
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL, User.class)
                .getResultList();
    }

    @Override
    public void deleteAll() {
        em.getTransaction().begin();
        em.createNamedQuery(User.DELETE_ALL).executeUpdate();
        em.getTransaction().commit();
    }
}
