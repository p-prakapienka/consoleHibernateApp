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
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } else {
            User updated = em.merge(user);
            em.getTransaction().commit();
            return updated;
        }
    }

    @Override
    public boolean delete(int id) {
        em.getTransaction().begin();
        int result = em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
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
