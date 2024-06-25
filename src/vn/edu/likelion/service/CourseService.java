package vn.edu.likelion.service;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;
import vn.edu.likelion.utils.Instant;
import vn.edu.likelion.utils.Validator;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
Class CourseService will implement GeneralInterface Interface
This class will implement 4 function: add, update, printList, delete.
It will also manage course
 */
public class CourseService implements GeneralInterface {

    private static Scanner sc;

    public CourseService() {
        sc = new Scanner(System.in);
    }

    // Build function add.
    @Override
    public void add(List<Course> listCourses, List<Trainee> listTrainee) {
        Course course = new Course();

        // Enter ID of course
        while (true){
            System.out.print("Enter the ID of course: ");
            String id = sc.nextLine();

            // Check duplicate ID existed in list of course
            if(Validator.checkDuplicateValue(listCourses, id, "ID", null)){
                System.out.println("This ID is existed in the list. Please. enter again!");
            }else{
                course.setId(id); // if ID don't exist, ID will be setted into object.
                break;
            }
        }

        // call function enter name
        String name = enterName(listCourses, course.getId());
        course.setName(name);

        // call function enter amount with parameter "ADD"
        int amount = enterAmount("ADD");
        course.setAmount(amount);

        listCourses.add(course); // add object course into list.
    }

    // build function print list
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

    // Build function update.
    @Override
    public void update(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        boolean flag = false; // put a variable flag

        // run for-each to find out course
        for (Course course : listCourse){
            if(id.equals(course.getId())){
                // After get a course, let's enter the name
                String name = enterName(listCourse, id);
                course.setName(name);

                // Enter the amount, this amount will be added amount of course currently.
                int amount = 0;
                while (true){
                    amount = enterAmount("UPDATE"); // call function enter the amount with parameter "UPDATE"
                    // if amount is added over 3 , it will be noticed.
                    if(course.getAmount() + amount > Instant.MAXIMUM_TRAINEE_OF_COURSE){
                        System.out.println(">> Maximum of course is three.");
                    }else{
                        course.setAmount(course.getAmount() + amount); // In case of enough 3
                        break;
                    }
                }

                flag = true; // assign flag to true
                break;
            }
        }
        // if flag is false, this ID won't be existed in list of course.
        if (!flag){
            throw new NotFoundException("Not found ID: " + id + " in this list."); // throw one exception
        }
    }

    // build function delete
    @Override
    public void delete(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Course course = findCourse(id, listCourse); // find a course by id.

        // if course is null , it will throw a exception
        if (course == null) {
            throw new NotFoundException("Not found ID: " + id + " in this list.");
        }

        // use iterator to find trainee who registered that course by id of trainee
        // using iterator can remove any object without bug
        Iterator<Trainee> iteratorTrainee = listTrainee.iterator();
        while (iteratorTrainee.hasNext()){
            Trainee trainee = iteratorTrainee.next();
            if(trainee.getCourse().getId().equals(id)){
                iteratorTrainee.remove();
            }
        }

        // after removing all trainee relevant this course, so remove that course.
        listCourse.remove(course);
    }

    // build function print one detail course
    public static void printOneCourseDetail(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Course course = findCourse(id, listCourse); // search a course
        if(course == null){
            // throw one exception if coourse is null
            throw new NotFoundException("Not found ID: " + id + " in this list.");
        }

        // print name of course and list of trainee registered that course
        System.out.println("Name of course: " + course.getName());
        boolean flag = false;

        // run for-each to print all trainees
        for (Trainee trainee : listTrainee){
            if(trainee.getCourse().getId().equals(id)){
                System.out.println(trainee.getName() + "\t\t" + trainee.getAge() + "\t\t" + trainee.getDateParticipate());
                flag = true;
            }
        }

        // notice that list trainee is empty.
        if(!flag){
            System.out.println("List trainee of this course is empty.");
        }
    }

    // find a course by id
    public static Course findCourse(String id, List<Course> listCourse){
        for (Course course : listCourse){
            if(id.equals(course.getId())){
                return course;
            }
        }
        return null;
    }

    // build a function to enter the name of course
    private static String enterName(List<Course> listCourses, String id){
        while (true){
            System.out.print("Enter the name of course: ");
            String name = sc.nextLine();

            // check duplicate name of course. It must unique
            if(Validator.checkDuplicateValue(listCourses, name, "NAME", id)){
                System.out.println("This name is existed in the list. Please. enter again!");
            }else{
                return name; // return name if it doesn't exist before
            }
        }
    }

    // build a function to enter amount of course
    private static int enterAmount(String type){
        while (true){
            try {
                System.out.print("Enter the amount of course(maximum is three): ");
                int amount = Integer.parseInt(sc.nextLine());

                // type is add. Amount is from 1 to 3
                if(type.equals("ADD")){
                    if(amount <= 0 || amount > 3){
                        System.out.println("Amount of trainee is from 1 to 3. Please, enter again!");
                    }else{
                        return amount;
                    }
                }else{
                     // type is update. Amount is from 0 to 3
                    if(amount < 0 || amount > 3){
                        System.out.println("Amount of trainee is from 0 to 3. Please, enter again!");
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