package vn.edu.likelion.service;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;
import vn.edu.likelion.utils.Instant;
import vn.edu.likelion.utils.Validator;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CourseService implements GeneralInterface {

    private static Scanner sc;

    public CourseService() {
        sc = new Scanner(System.in);
    }

    @Override
    public void add(List<Course> listCourses, List<Trainee> listTrainee) {
        Course course = new Course();

        while (true){
            System.out.print("Enter the ID of course: ");
            String id = sc.nextLine();

            if(Validator.checkDuplicateValue(listCourses, id, "ID", null)){
                System.out.println("This ID is existed in the list. Please. enter again!");
            }else{
                course.setId(id);
                break;
            }
        }

        String name = enterName(listCourses, course.getId());
        course.setName(name);

        int amount = enterAmount("ADD");
        course.setAmount(amount);

        listCourses.add(course);
    }

    @Override
    public void update(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        boolean flag = false;

        for (Course course : listCourse){
            if(id.equals(course.getId())){
                String name = enterName(listCourse, id);
                int amount = 0;
                while (true){
                    amount = enterAmount("UPDATE");
                    if(course.getAmount() + amount > Instant.MAXIMUM_TRAINEE_OF_COURSE){
                        System.out.println(">> Maximum of course is three.");
                    }else{
                        course.setAmount(course.getAmount() + amount);
                        break;
                    }
                }
                course.setName(name);
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new NotFoundException("Not found ID: " + id + " in this list.");
        }
    }


    @Override
    public void printList(List<?> list) {
        List<Course> listCourses = (List<Course>) list;

        if(listCourses.isEmpty()){
            System.out.println("List course is empty");
        }else{
            int i = 0;
            System.out.println("Order \t\t" + "ID    \t\t" + "Name \t\t" + "Amount \t\t\t" + "Status \t\t");
            for (Course course : listCourses){
                System.out.println(++i + "\t\t\t" + course.getId() + "\t\t\t" + course.getName() + "\t\t\t" + course.getRegistered() + "/" + course.getAmount() + "\t\t\t\t" +
                        (course.isStatus() ? "Open" : "Close"));
            }
        }

    }

    @Override
    public void delete(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Course course = findCourse(id, listCourse);
        if (course == null) {
            throw new NotFoundException("Not found ID: " + id + " in this list.");
        }

        Iterator<Trainee> iteratorTrainee = listTrainee.iterator();
        while (iteratorTrainee.hasNext()){
            Trainee trainee = iteratorTrainee.next();
            if(trainee.getCourse().getId().equals(id)){
                iteratorTrainee.remove();
            }
        }

        listCourse.remove(course);
    }


    public static Course findCourse(String id, List<Course> listCourse){
        for (Course course : listCourse){
            if(id.equals(course.getId())){
                return course;
            }
        }
        return null;
    }

    public static void printOneCourseDetail(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Course course = findCourse(id, listCourse);
        if(course == null){
            throw new NotFoundException("Not found ID: " + id + " in this list.");
        }

        System.out.println("Name of course: " + course.getName());
        boolean flag = false;
        for (Trainee trainee : listTrainee){
            if(trainee.getCourse().getId().equals(id)){
                System.out.println(trainee.getName() + "\t\t" + trainee.getAge() + "\t\t" + trainee.getDateParticipate());
                flag = true;
            }
        }

        if(!flag){
            System.out.println("List trainee of this course is empty.");
        }
    }

    private static String enterName(List<Course> listCourses, String id){
        while (true){
            System.out.print("Enter the name of course: ");
            String name = sc.nextLine();

            if(Validator.checkDuplicateValue(listCourses, name, "NAME", id)){
                System.out.println("This name is existed in the list. Please. enter again!");
            }else{
                return name;
            }
        }
    }

    private static int enterAmount(String type){
        while (true){
            try {
                System.out.print("Enter the amount of course(maximum is three): ");
                int amount = Integer.parseInt(sc.nextLine());

                if(type.equals("ADD")){
                    if(amount <= 0 || amount > 3){
                        System.out.println("Amount of trainee is between 1 and 3. Please, enter again!");
                    }else{
                        return amount;
                    }
                }else{
                    if(amount < 0 || amount > 3){
                        System.out.println("Amount of trainee is between 0 and 3. Please, enter again!");
                    }else{
                        return amount;
                    }
                }
            }catch (NumberFormatException ex){
                System.out.println("Invalid value. Please, enter again!");
            }
        }
    }
}
