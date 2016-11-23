package by.prakapienka.at13java.dao;

import org.junit.After;
import org.junit.Before;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public abstract class AbstractDaoTest {

    EntityManager em;

    @Before
    public void setUp() {
        em = Persistence.createEntityManagerFactory("USERORDERS").createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }
}
