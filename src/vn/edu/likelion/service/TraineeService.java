package vn.edu.likelion.service;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;
import vn.edu.likelion.utils.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TraineeService implements GeneralInterface{
    private static Scanner sc;
    private static CourseService courseService = null;

    public TraineeService() {
        sc = new Scanner(System.in);
        courseService = new CourseService();
    }


    @Override
    public void add(List<Course> listCourse, List<Trainee> listTrainee) {
        Trainee trainee = new Trainee();

        choiceCourse(listCourse, trainee);

        while (true){
            System.out.print("Enter the ID of trainee: ");
            String id = sc.nextLine();

            if(Validator.checkDuplicateByIdFromTrainee(listTrainee, id)){
                System.out.println("This ID is existed in the list trainee. Please. enter again!");
            }else{
                trainee.setId(id);
                break;
            }
        }

        System.out.print("Enter the name of trainee: ");
        String name = sc.nextLine();
        trainee.setName(name);

        int age = enterAge();
        trainee.setAge(age);

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = date.format(formatter);
        trainee.setDateParticipate(formattedDateTime);

        Course course = trainee.getCourse();

        course.setRegistered(course.getRegistered() + 1);
        if (course.getAmount() == course.getRegistered()){
            course.setStatus(true);
        }

        listTrainee.add(trainee);
    }

    public static Trainee findOutTrainee(String id, List<?> list){
        List<Trainee> listTrainees = (List<Trainee>) list;
        for (Trainee trainee : listTrainees){
            if(id.equals(trainee.getId())){
                return trainee;
            }
        }
        return null;
    }

    @Override
    public void update(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Trainee trainee = findOutTrainee(id, listTrainee);
        if (trainee == null){
            throw new NotFoundException("Not found ID: " + id + " in this list");
        }

        System.out.print("Enter the name of trainee: ");
        String name = sc.nextLine();
        trainee.setName(name);

        int age = enterAge();
        trainee.setAge(age);

        choiceCourse(listCourse, trainee);
    }

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

    @Override
    public void delete(String id, List<Course> listCourse, List<Trainee> listTrainee) throws NotFoundException {
        Trainee trainee = findOutTrainee(id, listTrainee);
        if (trainee == null){
            throw new NotFoundException("Not found ID: " + id + " in this list");
        }
        String idCourse = trainee.getCourse().getId();
        listTrainee.remove(trainee);

        Course course = CourseService.findCourse(idCourse, listCourse);
        course.setRegistered(course.getRegistered() - 1);
        if(course.getRegistered() == 0){
            course.setStatus(false);
        }
    }

    private static void choiceCourse(List<Course> listCourse, Trainee trainee){
        Course course = null;
        while (true){
            System.out.println(">>> List of course:");
            courseService.printList(listCourse);

            System.out.print("Enter the id of course: ");
            String id = sc.nextLine();

            course = CourseService.findCourse(id, listCourse);
            if(course == null){
                System.out.println("Not found " + id + " .Please, enter again.");
            } else if (course.getAmount() == course.getRegistered()) {
                System.out.println("This course is full.");
            } else{
                if(trainee.getCourse() != null && !trainee.getCourse().getId().equals(course.getId())){
                    Course oldCourse = trainee.getCourse();
                    oldCourse.setRegistered(oldCourse.getRegistered() - 1);
                    if (oldCourse.getRegistered() == 0){
                        oldCourse.setStatus(false);
                    }

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
