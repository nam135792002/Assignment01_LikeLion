package vn.edu.likelion.entity;

import java.time.LocalDate;

public class Trainee extends InfoGeneral{
    private int age;
    private String dateParticipate;
    private Course course;

    public Trainee() {}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDateParticipate() {
        return dateParticipate;
    }

    public void setDateParticipate(String dateParticipate) {
        this.dateParticipate = dateParticipate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "age=" + age +
                ", dateParticipate=" + dateParticipate +
                ", course=" + course +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
