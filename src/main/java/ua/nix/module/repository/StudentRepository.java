package ua.nix.module.repository;

import ua.nix.module.config.HibernateUtil;
import ua.nix.module.entity.Student;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements AbstractRepository<Student> {
    private final EntityManager entityManager;
    private static StudentRepository instance;

    private StudentRepository() {
        entityManager = HibernateUtil.getEntityManager();
    }

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    @Override
    public List<Student> get() {
        entityManager.getTransaction().begin();
        List<Student> students = entityManager.createQuery("from Student", Student.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return students;
    }

    @Override
    public Optional<Student> getById(String id) {
        entityManager.getTransaction().begin();
        Student student = entityManager.createQuery("from Student where id = :id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(student);
    }

    @Override
    public void create(Student object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public Student update(Student object) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Student set name = :name, age = :age, enterUniversity = :date where id = :id")
                .setParameter("age", object.getAge())
                .setParameter("date", object.getEnterUniversity())
                .setParameter("name", object.getName())
                .setParameter("id", object.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Override
    public Student delete(String id) {
        Student student = getById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return student;
    }

    public List<Student> getStudentsWithHigherAvgMark(double mark){
        entityManager.getTransaction().begin();
        List<Student> students = entityManager.createNativeQuery("select * from student join mark m " +
                        "on student.id = m.student_id group by student_id having avg(value) > :mark", Student.class)
                .setParameter("mark",mark)
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return students;
    }
}
