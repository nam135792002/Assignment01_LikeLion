package vn.edu.likelion.service;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;
import vn.edu.likelion.utils.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/*
Class TraineeService will implement GeneralInterface Interface
This class will implement 4 function: add, update, printList, delete.
It will also manage trainee
 */
public class TraineeService implements GeneralInterface{
    private static Scanner sc;
    private static CourseService courseService = null;

    public TraineeService() {
        sc = new Scanner(System.in);
        courseService = new CourseService();
    }

    // build a function to add trainee into list.
    @Override
    public void add(List<Course> listCourse, List<Trainee> listTrainee) {
        Trainee trainee = new Trainee();

        // List of course is showed, choice a course
        choiceCourse(listCourse, trainee);

        // enter the id of trainee
        while (true){
            System.out.print("Enter the ID of trainee: ");
            String id = sc.nextLine();

            // check validate id of trainee before.
            if(Validator.checkDuplicateByIdFromTrainee(listTrainee, id)){
                System.out.println("This ID is existed in the list trainee. Please. enter again!");
            }else{
                trainee.setId(id);
                break;
            }
        }

        // enter the name of trainee
        System.out.print("Enter the name of trainee: ");
        String name = sc.nextLine();
        trainee.setName(name);

        // enter the age of trainee
        int age = enterAge();
        trainee.setAge(age);

        // create real time by LocalDateTime, then format against pattern and transfer string.
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = date.format(formatter);
        trainee.setDateParticipate(formattedDateTime);

        // get a coursr from trainee.
        Course course = trainee.getCourse();

        // increase amount enroll in that course.
        course.setRegistered(course.getRegistered() + 1);
        if (course.getAmount() == course.getRegistered()){
            course.setStatus(true); // if course is full, change status to true: open.
        }

        listTrainee.add(trainee); // add a trainee into list.
    }

    // build function to print list of trainee
    @Override
    public void printList(List<?> list) {
        List<Trainee> listTrainees = (List<Trainee>) list;

        System.out.println("Order \t\t" + "ID    \t\t" + "Name \t\t\t\t\t\t" + "Age \t\t" + "Date of Attention \t\t" + "Name of Course");
        int i = 0;
        for (Trainee trainee : listTrainees){
            System.out.println(++i + "\t\t\t" + trainee.getId() + "\t\t\t" + trainee.getName() + "\t\t\t" + trainee.getAge() + "\t\t\t" +
                    trainee.getDateParticipate() + "\t\t\t" + trainee.getCourse().getName());
        }
    }

    // build function update
    @Override
    public void update(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        // search trainee by if
        Trainee trainee = findOutTrainee(id, listTrainee);
        if (trainee == null){
            // throw one exception if ID doesn't exist
            throw new NotFoundException("Not found ID: " + id + " in this list");
        }

        // enter the name
        System.out.print("Enter the name of trainee: ");
        String name = sc.nextLine();
        trainee.setName(name);

        //enter the age
        int age = enterAge();
        trainee.setAge(age);

        // choice a course.
        choiceCourse(listCourse, trainee);
    }

    // build remove a trainee
    @Override
    public void delete(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        // search trainee by if
        Trainee trainee = findOutTrainee(id, listTrainee);
        if (trainee == null){
            // throw one exception if ID doesn't exist
            throw new NotFoundException("Not found ID: " + id + " in this list");
        }

        String idCourse = trainee.getCourse().getId(); // get id of trainee
        listTrainee.remove(trainee); // call function remove

        // get a course, decrease amount of course when remove a trainee
        Course course = CourseService.findCourse(idCourse, listCourse);
        course.setRegistered(course.getRegistered() - 1);
        if(course.getRegistered() == 0){
            course.setStatus(false); // in case of remove all trainee of course, change status to false = close
        }
    }

    // find out a trainee by id
    public static Trainee findOutTrainee(String id, List<?> list){
        List<Trainee> listTrainees = (List<Trainee>) list;
        for (Trainee trainee : listTrainees){
            if(id.equals(trainee.getId())){
                return trainee;
            }
        }
        return null; // return null if id doesn't exist
    }

    // build function choice course
    private static void choiceCourse(List<Course> listCourse, Trainee trainee){
        Course course = null;
        while (true){
            // show list of course
            System.out.println(">>> List of course:");
            courseService.printList(listCourse);

            // enter the id
            System.out.print("Enter the id of course: ");
            String id = sc.nextLine();

            course = CourseService.findCourse(id, listCourse); // find course by id
            if(course == null){
                System.out.println("Not found " + id + " .Please, enter again."); // not found course
            } else if (course.getAmount() == course.getRegistered()) {
                System.out.println("This course is full."); // amount of course is full
            } else{

                // trainee has registered course, and it must be different new course
                if(trainee.getCourse() != null && !trainee.getCourse().getId().equals(course.getId())){
                    // when trainee change other course, amount of old course is decrease
                    Course oldCourse = trainee.getCourse();
                    oldCourse.setRegistered(oldCourse.getRegistered() - 1);
                    if (oldCourse.getRegistered() == 0){
                        oldCourse.setStatus(false);
                    }

                    // amount of new course is increse
                    course.setRegistered(course.getRegistered() + 1);
                    if (course.getAmount() == course.getRegistered()){
                        course.setStatus(true);
                    }
                }

                trainee.setCourse(course);
                break;
            }
        }
    }

    // build function enter age
    public static int enterAge(){
        while (true){
            try {
                System.out.print("Enter the age of trainee: ");
                int age = Integer.parseInt(sc.nextLine());

                return age;
            }catch (NumberFormatException ex){
                System.out.println("Invalid value. Please, enter again!");
            }
        }
    }
}
