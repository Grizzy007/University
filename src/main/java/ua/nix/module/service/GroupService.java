package ua.nix.module.service;

import ua.nix.module.entity.Class;
import ua.nix.module.entity.Student;
import ua.nix.module.repository.GroupRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupService implements AbstractService<Class> {
    private final GroupRepository repository;
    private static GroupService instance;

    private GroupService() {
        repository = GroupRepository.getInstance();
    }

    public static GroupService getInstance() {
        if (instance == null) {
            instance = new GroupService();
        }
        return instance;
    }

    @Override
    public void create(Class obj) {
        repository.create(obj);
    }

    @Override
    public List<Class> getAll() {
        return repository.get();
    }

    @Override
    public Class generate() {
        List<Student> students = new ArrayList<>(15);
        StudentService service = StudentService.getInstance();
        for (int i = 0; i < 15; i++) {
            students.add(service.generate());
        }
        return new Class("Class" + RANDOM.nextInt(100),students);
    }

    public void findByName(BufferedReader reader) throws IOException {
        System.out.println("Input name to find group: ");
        String name = reader.readLine();
        List<String> groups = repository.getNames(name);
        groups.forEach(System.out::println);
    }

    public void getCount() {
        repository.getCount();
    }
}
