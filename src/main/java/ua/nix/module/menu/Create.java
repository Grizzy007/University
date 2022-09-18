package ua.nix.module.menu;

import ua.nix.module.service.*;

import java.io.BufferedReader;
import java.io.IOException;

public class Create implements Command{
    private static final GroupService GROUP_SERVICE = GroupService.getInstance();
    private static final StudentService STUDENT_SERVICE = StudentService.getInstance();
    private static final SubjectService SUBJECT_SERVICE = SubjectService.getInstance();
    private static final TeacherService TEACHER_SERVICE = TeacherService.getInstance();
    private static final MarkService MARK_SERVICE = MarkService.getInstance();
    @Override
    public void execute(BufferedReader reader) throws IOException {
        System.out.println("""
                What kind of university object do you want to create:
                1) Group;
                2) Student;
                3) Subject;
                4) Teacher;
                5) Mark.
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1 -> GROUP_SERVICE.create(GROUP_SERVICE.generate());
            case 2 -> STUDENT_SERVICE.create(STUDENT_SERVICE.generate());
            case 3 -> SUBJECT_SERVICE.create(SUBJECT_SERVICE.generate());
            case 4 -> TEACHER_SERVICE.create(TEACHER_SERVICE.generate());
            case 5 -> MARK_SERVICE.create(MARK_SERVICE.generate());
            default -> System.out.println("Incorrect number!");
        }
    }
}
