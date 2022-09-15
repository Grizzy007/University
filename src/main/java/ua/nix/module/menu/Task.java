package ua.nix.module.menu;

import ua.nix.module.service.*;

import java.io.BufferedReader;
import java.io.IOException;

public class Task implements Command {
    private static final GroupService GROUP_SERVICE = GroupService.getInstance();
    private static final StudentService STUDENT_SERVICE = StudentService.getInstance();
    private static final SubjectService SUBJECT_SERVICE = SubjectService.getInstance();
    private static final TeacherService TEACHER_SERVICE = TeacherService.getInstance();
    private static final MarkService MARK_SERVICE = MarkService.getInstance();

    @Override
    public void execute(BufferedReader reader) throws IOException {
        System.out.println("""
                What kind of task do you want to execute:
                1) Find group by name;
                2) Count of students in every group;
                3) Average mark of every group;
                4) Teacher by name or surname;
                5) Best and worst marked subjects;
                6) Students with average mark higher than inputted;
                """);
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1 -> GROUP_SERVICE.findByName(reader);
            case 2 -> GROUP_SERVICE.getCount();
            case 3 -> GROUP_SERVICE.getGroupAvgMark();
            case 4 -> TEACHER_SERVICE.findTeacher(reader);
            case 5 -> SUBJECT_SERVICE.getSubjects();
            case 6 -> STUDENT_SERVICE.getStudentsWithHigherAvgMark(reader);
            default -> System.out.println("Incorrect number!");
        }
    }
}
