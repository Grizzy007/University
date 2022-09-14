package ua.nix.module.service;

import ua.nix.module.entity.Student;
import ua.nix.module.entity.Subject;
import ua.nix.module.entity.Teacher;
import ua.nix.module.repository.SubjectRepository;
import ua.nix.module.repository.TeacherRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TeacherService implements AbstractService<Teacher> {
    private final TeacherRepository repository;
    private static TeacherService instance;

    private TeacherService() {
        repository = TeacherRepository.getInstance();
    }

    public static TeacherService getInstance() {
        if (instance == null) {
            instance = new TeacherService();
        }
        return instance;
    }

    @Override
    public void create(Teacher obj) {
        repository.create(obj);
    }

    @Override
    public List<Teacher> getAll() {
        return repository.get();
    }

    @Override
    public Teacher generate() {
        SubjectService service = SubjectService.getInstance();
        return new Teacher("Name" + RANDOM.nextInt(1000),
                "Surname" + RANDOM.nextInt(1000),
                RANDOM.nextInt(21, 100),
                service.generate());
    }

    public void findTeacher(BufferedReader reader) throws IOException {
        System.out.println("Input name or surname of teacher: ");
        String name = reader.readLine();
        System.out.println(repository.findTeacher(name));
    }
}
