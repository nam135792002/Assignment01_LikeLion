package vn.edu.likelion.app;

import vn.edu.likelion.entity.Course;
import vn.edu.likelion.entity.Trainee;
import vn.edu.likelion.exception.NotFoundException;
import vn.edu.likelion.service.CourseService;
import vn.edu.likelion.service.TraineeService;
import vn.edu.likelion.utils.Instant;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CourseService courseService = new CourseService();
        TraineeService traineeService = new TraineeService();
        List<Course> listCourses = new LinkedList<>();
        List<Trainee> listTrainee = new LinkedList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println(">>MENU..");
            System.out.println("-----* Manage Course *-----");
            System.out.println("1. Add one course.");
            System.out.println("2. Print one detail course.");
            System.out.println("3. Print list course.");
            System.out.println("4. Update a course.");
            System.out.println("5. Delete a course.");
            System.out.println("-----* Manage Trainee *-----");
            System.out.println("6. Add one trainee.");
            System.out.println("7. Print list trainee.");
            System.out.println("8. Update a trainee.");
            System.out.println("9. Delete a trainee.");
            System.out.println("10. Exit.");
            System.out.println("---------------------------------");

            int choice = 0;
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid value. Enter a number between 1 and 10.");
                continue; // Continue to the next iteration of the loop
            }

            switch (choice) {
                case 1 -> {
                    if (listCourses.size() == Instant.MAXIMUM_COURSE) {
                        System.out.println("Maximum of list course is five.");
                    } else courseService.add(listCourses, listTrainee);
                }
                case 2 -> {
                    if (!listCourses.isEmpty()) {
                        System.out.print("Enter the ID of course: ");
                        String courseId = sc.nextLine();
                        try {
                            CourseService.printOneCourseDetail(courseId, listCourses, listTrainee);
                        } catch (NotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("List of course is empty");
                    }
                }
                case 3 -> {
                    if (!listCourses.isEmpty()) {
                        courseService.printList(listCourses);
                    } else {
                        System.out.println("List of course is empty");
                    }
                }
                case 4 -> {
                    if (!listCourses.isEmpty()) {
                        System.out.print("Enter the ID of course: ");
                        String courseIdToUpdate = sc.nextLine();
                        try {
                            courseService.update(courseIdToUpdate, listCourses, listTrainee);
                        } catch (NotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("List of course is empty");
                    }
                }
                case 5 -> {
                    if (!listCourses.isEmpty()) {
                        System.out.print("Enter the ID of course: ");
                        String courseIdToDelete = sc.nextLine();
                        try {
                            courseService.delete(courseIdToDelete, listCourses, listTrainee);
                        } catch (NotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("List of course is empty");
                    }
                }
                case 6 -> {
                    if (!listCourses.isEmpty()) {
                        traineeService.add(listCourses, listTrainee);
                    } else {
                        System.out.println("List of course is empty");
                    }
                }
                case 7 -> {
                    if (!listTrainee.isEmpty()) {
                        traineeService.printList(listTrainee);
                    } else {
                        System.out.println("List of trainee is empty");
                    }
                }
                case 8 -> {
                    if (!listTrainee.isEmpty()) {
                        System.out.print("Enter the ID of trainee: ");
                        String traineeIdToUpdate = sc.nextLine();
                        try {
                            traineeService.update(traineeIdToUpdate, listCourses, listTrainee);
                        } catch (NotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("List of trainee is empty");
                    }
                }
                case 9 -> {
                    if (!listTrainee.isEmpty()) {
                        System.out.print("Enter the ID of trainee: ");
                        String traineeIdToDelete = sc.nextLine();
                        try {
                            traineeService.delete(traineeIdToDelete, listCourses, listTrainee);
                        } catch (NotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        System.out.println("List of trainee is empty");
                    }
                }
                case 10 -> {
                    System.out.println("Exiting the program.");
                    return; // Exit the program
                }
                default -> System.out.println("Invalid choice. Enter a number between 1 and 10.");
            }

            System.out.println("\n-------------------------**----------------------\n");
        }
    }
}
