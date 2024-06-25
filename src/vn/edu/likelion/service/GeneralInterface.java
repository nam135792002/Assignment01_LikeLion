package vn.edu.likelion.service;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;

import java.util.List;

public interface GeneralInterface {
    void add(List<Course> listCourse, List<Trainee> listTrainee);
    void update(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException;
    void printList(List<?> list);
    void delete(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException;
}
