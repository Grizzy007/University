package ua.nix.module.service;

import ua.nix.module.entity.Mark;
import ua.nix.module.entity.Student;
import ua.nix.module.repository.StudentRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements AbstractService<Student> {
    private final StudentRepository repository;
    private static StudentService instance;

    private StudentService() {
        repository = StudentRepository.getInstance();
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    @Override
    public void create(Student obj) {
        repository.create(obj);
    }

    @Override
    public List<Student> getAll() {
        return repository.get();
    }

    @Override
    public Student generate() {
        Student student = new Student("Name" + RANDOM.nextInt(1000),
                "Surname" + RANDOM.nextInt(1000),
                RANDOM.nextInt(16, 100),
                LocalDate.now().minusDays(RANDOM.nextInt(100)));
        MarkService service = MarkService.getInstance();
        List<Mark> marks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            marks.add(service.generateToStudent(student));
        }
        student.setMarks(marks);
        return student;
    }

    public void getStudentsWithHigherAvgMark(BufferedReader reader) throws IOException {
        System.out.println("Input name to find group: ");
        Double mark = Double.parseDouble(reader.readLine());
        repository.getStudentsWithHigherAvgMark(mark).forEach(System.out::println);
    }
}
