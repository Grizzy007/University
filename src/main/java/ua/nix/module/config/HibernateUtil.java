package ua.nix.module.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManager entityManager;

    private HibernateUtil() {
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
}
