package ua.nix.module.entity;

import javax.persistence.*;

@Entity
public class Teacher extends Person {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Teacher() {
    }

    public Teacher(String name, String surname, int age, Subject subjects) {
        super(name, surname, age);
        this.subject = subjects;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher name=" + name +
                ", surname=" + surname +
                ", age=" + age +
                ", subject=" + subject + '\n';
    }
}
