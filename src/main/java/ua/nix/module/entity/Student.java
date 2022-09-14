package ua.nix.module.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Student extends Person {
    private LocalDate enterUniversity;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Mark> marks;

    public Student() {
    }

    public Student(String name, String surname, int age, LocalDate enterUniversity) {
        super(name, surname, age);
        this.enterUniversity = enterUniversity;
    }

    public LocalDate getEnterUniversity() {
        return enterUniversity;
    }

    public void setEnterUniversity(LocalDate enterUniversity) {
        this.enterUniversity = enterUniversity;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Student name=" + name +
                ", surname=" + surname +
                ", age=" + age +
                ", enterUniversity=" + enterUniversity +
                ", marks=" + marks + '\n';
    }
}
