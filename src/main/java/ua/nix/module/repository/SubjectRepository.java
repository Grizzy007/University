package ua.nix.module.repository;

import ua.nix.module.config.HibernateUtil;
import ua.nix.module.entity.Subject;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class SubjectRepository implements AbstractRepository<Subject> {
    private final EntityManager entityManager;
    private static SubjectRepository instance;

    private SubjectRepository() {
        entityManager = HibernateUtil.getEntityManager();
    }

    public static SubjectRepository getInstance() {
        if (instance == null) {
            instance = new SubjectRepository();
        }
        return instance;
    }

    @Override
    public List<Subject> get() {
        entityManager.getTransaction().begin();
        List<Subject> subjects = entityManager.createQuery("from Subject", Subject.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return subjects;
    }

    @Override
    public Optional<Subject> getById(String id) {
        entityManager.getTransaction().begin();
        Subject subject = entityManager.createQuery("from Subject where id = :id", Subject.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(subject);
    }

    @Override
    public void create(Subject object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public Subject update(Subject object) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Subject set name = :name, code = :code where id = :id")
                .setParameter("name", object.getName())
                .setParameter("code", object.getCode())
                .setParameter("id", object.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Override
    public Subject delete(String id) {
        Subject subject = getById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(subject);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return subject;
    }

    public void getBestMarked() {
        entityManager.getTransaction().begin();
        System.out.println("Subject with best marks - " + entityManager
                .createNativeQuery("select s.name from subject s " +
                        "join mark m on s.id = m.subject_id group by s.id order by avg(m.value) desc")
                .setMaxResults(1)
                .getSingleResult() + ", average mark = " + entityManager
                .createNativeQuery("select avg(m.value) from subject s " +
                        "join mark m on s.id = m.subject_id group by s.id order by avg(m.value) desc")
                .setMaxResults(1)
                .getSingleResult());
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public void getWorstMarked() {
        entityManager.getTransaction().begin();
        System.out.println("Subject with worst marks - " + entityManager
                .createNativeQuery("select s.name from subject s " +
                        "join mark m on s.id = m.subject_id group by s.id order by avg(m.value)")
                .setMaxResults(1)
                .getSingleResult() + ", average mark = " + entityManager
                .createNativeQuery("select avg(m.value) from subject s " +
                        "join mark m on s.id = m.subject_id group by s.id order by avg(m.value)")
                .setMaxResults(1)
                .getSingleResult());
        entityManager.flush();
        entityManager.getTransaction().commit();
    }
}
