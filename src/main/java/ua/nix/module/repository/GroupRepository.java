package ua.nix.module.repository;

import ua.nix.module.config.HibernateUtil;
import ua.nix.module.entity.Class;
import ua.nix.module.entity.Mark;

import javax.persistence.EntityManager;
import java.util.*;

public class GroupRepository implements AbstractRepository<Class> {
    private final EntityManager entityManager;
    private static GroupRepository instance;

    private GroupRepository() {
        entityManager = HibernateUtil.getEntityManager();
    }

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    @Override
    public List<Class> get() {
        entityManager.getTransaction().begin();
        List<Class> groups = entityManager.createQuery("from Class", Class.class).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return groups;
    }

    @Override
    public Optional<Class> getById(String id) {
        entityManager.getTransaction().begin();
        Class group = entityManager.createQuery("from Class where id = :id", Class.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(group);
    }

    @Override
    public void create(Class object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public Class update(Class object) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Class set name = :newName where name = :name")
                .setParameter("newName", object.getName())
                .setParameter("name", object.getName())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return object;
    }

    @Override
    public Class delete(String id) {
        Class group = getById(id).get();
        entityManager.getTransaction().begin();
        entityManager.remove(group);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return group;
    }

    public List<String> getNames(String name) {
        entityManager.getTransaction().begin();
        List<String> names = entityManager.createQuery("select name from Class where name like :name", String.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return names;
    }

    public void getCount() {
        Map<String, Integer> map = new HashMap<>();
        entityManager.getTransaction().begin();
        List count = entityManager
                .createNativeQuery("select count(students_id) from class_student cs group by Class_id")
                .getResultList();
        List names = entityManager
                .createNativeQuery("select name from class join class_student cs on class.id = cs.Class_id group by Class_id")
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        Iterator iter1 = count.iterator();
        Iterator iter2 = names.iterator();
        while (iter1.hasNext() && iter2.hasNext()) {
            System.out.println("Group - " + iter2.next() + ", count of students - " + iter1.next());
        }
    }

    public List<Long> getGroupsAvgMark() {
        return get().stream()
                .map(group -> group.getStudents().stream()
                        .map(student -> student.getMarks().stream()
                                .map(Mark::getValue)
                                .reduce(Integer::sum).orElse(1) / student.getMarks().stream().count())
                        .reduce(Long::sum).orElse(1L) / group.getStudents().stream().count()).toList();

    }
}
