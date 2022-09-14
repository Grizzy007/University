package ua.nix.module.service;

import ua.nix.module.entity.Class;
import ua.nix.module.entity.Mark;
import ua.nix.module.entity.Student;
import ua.nix.module.repository.GroupRepository;
import ua.nix.module.repository.MarkRepository;

import java.util.ArrayList;
import java.util.List;

public class MarkService implements AbstractService<Mark> {
    private final MarkRepository repository;
    private static MarkService instance;

    private MarkService() {
        repository = MarkRepository.getInstance();
    }

    public static MarkService getInstance() {
        if (instance == null) {
            instance = new MarkService();
        }
        return instance;
    }

    @Override
    public void create(Mark obj) {
        repository.create(obj);
    }

    @Override
    public List<Mark> getAll() {
        return repository.get();
    }

    @Override
    public Mark generate() {
        StudentService service = StudentService.getInstance();
        SubjectService subjectService = SubjectService.getInstance();
        return new Mark(RANDOM.nextInt(100), subjectService.generate(), service.generate());
    }
}
