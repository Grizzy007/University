package ua.nix.module.repository;

import ua.nix.module.config.HibernateUtil;
import ua.nix.module.entity.Mark;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class MarkRepository implements AbstractRepository<Mark> {
    private final EntityManager entityManager;
    private static MarkRepository instance;

    private MarkRepository() {
        entityManager = HibernateUtil.getEntityManager();
    }

    public static MarkRepository getInstance() {
        if (instance == null) {
            instance = new MarkRepository();
        }
        return instance;
    }

    @Override
    public List<Mark> get() {
        entityManager.getTransaction().begin();
        List<Mark> marks = entityManager.createQuery("from Class", Mark.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return marks;
    }

    @Override
    public Optional<Mark> getById(String id) {
        entityManager.getTransaction().begin();
        Mark mark = entityManager.createQuery("from Class where id = :id", Mark.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(mark);
    }

    @Override
    public void create(Mark object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public Mark update(Mark object) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Mark set value = :value where id = :id")
                .setParameter("value", object.getValue())
                .setParameter("id", object.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Override
    public Mark delete(String id) {
        Mark mark = getById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(mark);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return mark;
    }
}
