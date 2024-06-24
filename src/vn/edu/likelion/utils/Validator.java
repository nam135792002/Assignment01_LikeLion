package vn.edu.likelion.utils;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;

import java.util.List;

public class Validator {

    public static boolean checkDuplicateValue(List<Course> listCourses, String value, String type, String id){
        for (Course course : listCourses){
            if(type.equals("ID"))
                if(value.equals(course.getId())) return true;
            if(type.equals("NAME"))
                if(value.equalsIgnoreCase(course.getName()) && !id.equals(course.getId())) return true;
        }
        return false;
    }

    public static boolean checkDuplicateByIdFromTrainee(List<Trainee> listTrainee, String id){
        for (Trainee trainee : listTrainee){
            if(id.equals(trainee.getId())) return true;
        }
        return false;
    }
}
