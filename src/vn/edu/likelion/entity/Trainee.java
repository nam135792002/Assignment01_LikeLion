package vn.edu.likelion.entity;

import java.time.LocalDate;

public class Trainee extends InfoGeneral{
    private int age;
    private String dateParticipate; // date of enroll
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
}
