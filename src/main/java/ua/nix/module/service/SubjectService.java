package ua.nix.module.service;

import ua.nix.module.entity.Student;
import ua.nix.module.entity.Subject;
import ua.nix.module.repository.StudentRepository;
import ua.nix.module.repository.SubjectRepository;

import java.util.List;

public class SubjectService implements AbstractService<Subject> {
    private final SubjectRepository repository;
    private static SubjectService instance;

    private SubjectService() {
        repository = SubjectRepository.getInstance();
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService();
        }
        return instance;
    }

    @Override
    public void create(Subject obj) {
        repository.create(obj);
    }

    @Override
    public List<Subject> getAll() {
        return repository.get();
    }

    @Override
    public Subject generate() {
        Subject subject= new Subject();
        subject.setCode(RANDOM.nextInt(500));
        subject.setName("Subject"+subject.getCode());
        return subject;
    }
}
