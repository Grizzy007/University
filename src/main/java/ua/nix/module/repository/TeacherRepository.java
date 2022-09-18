package ua.nix.module.repository;

import ua.nix.module.config.HibernateUtil;
import ua.nix.module.entity.Teacher;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TeacherRepository implements AbstractRepository<Teacher> {
    private final EntityManager entityManager;
    private static TeacherRepository instance;

    private TeacherRepository() {
        entityManager = HibernateUtil.getEntityManager();
    }

    public static TeacherRepository getInstance() {
        if (instance == null) {
            instance = new TeacherRepository();
        }
        return instance;
    }

    @Override
    public List<Teacher> get() {
        entityManager.getTransaction().begin();
        List<Teacher> teachers = entityManager.createQuery("from Teacher", Teacher.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return teachers;
    }

    @Override
    public Optional<Teacher> getById(String id) {
        entityManager.getTransaction().begin();
        Teacher teacher = entityManager.createQuery("from Teacher where id = :id", Teacher.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(teacher);
    }

    @Override
    public void create(Teacher object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public Teacher update(Teacher object) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Teacher set name = :name, age = :age, subjects = :subs where name = :name")
                .setParameter("subs", object.getSubject())
                .setParameter("age", object.getAge())
                .setParameter("name", object.getName())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Override
    public Teacher delete(String id) {
        Teacher teacher = getById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(teacher);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return teacher;
    }

    public Teacher findTeacher(String findBy) {
        entityManager.getTransaction().begin();
        Teacher teacher = entityManager.createQuery("from Teacher where name=:name or surname=:surname", Teacher.class)
                .setParameter("name", findBy)
                .setParameter("surname", findBy)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return teacher;
    }
}
