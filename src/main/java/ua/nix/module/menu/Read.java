package ua.nix.module.menu;

import ua.nix.module.service.*;

import java.io.BufferedReader;
import java.io.IOException;

public class Read implements Command {
    private static final GroupService GROUP_SERVICE = GroupService.getInstance();
    private static final StudentService STUDENT_SERVICE = StudentService.getInstance();
    private static final SubjectService SUBJECT_SERVICE = SubjectService.getInstance();
    private static final TeacherService TEACHER_SERVICE = TeacherService.getInstance();
    private static final MarkService MARK_SERVICE = MarkService.getInstance();

    @Override
    public void execute(BufferedReader reader) throws IOException {
        System.out.println("""
                What kind of university object do you want to read:
                1) Group;
                2) Student;
                3) Subject;
                4) Teacher;
                5) Mark;
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1 -> System.out.println(GROUP_SERVICE.getAll());
            case 2 -> System.out.println(STUDENT_SERVICE.getAll());
            case 3 -> System.out.println(SUBJECT_SERVICE.getAll());
            case 4 -> System.out.println(TEACHER_SERVICE.getAll());
            case 5 -> System.out.println(MARK_SERVICE.getAll());
            default -> System.out.println("Incorrect number!");
        }
    }
}
