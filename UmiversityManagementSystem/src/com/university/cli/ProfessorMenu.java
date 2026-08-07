package com.university.cli;

import com.university.model.*;
import com.university.repository.CourseRepository;
import com.university.repository.UserRepository;
import com.university.service.*;
import java.util.List;
import java.util.Scanner;

public class ProfessorMenu {

    private Professor professor;
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private Scanner scanner;

    public ProfessorMenu(Professor professor) {
        this.professor = professor;
        this.courseRepository = CourseRepository.getInstance();
        this.userRepository = UserRepository.getInstance();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===== PROFESSOR MENU =====");
            System.out.println("1. View My Courses");
            System.out.println("2. Update Course Details");
            System.out.println("3. View Enrolled Students");
            System.out.println("4. View Course Feedback");
            System.out.println("0. Logout");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } 
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
                continue;
            }

            switch (choice) {
                case 1: viewMyCourses(); break;
                case 2: updateCourseDetails(); break;
                case 3: viewEnrolledStudents(); break;
                case 4: viewFeedback(); break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    private void viewFeedback() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        
        FeedbackService feedbackService = new FeedbackService();
        if(!professor.getAssignedCourses().contains(code)) {
            System.out.println("❌ Not your course");
            return;
        }

        List<Feedback<?>> feedbacks = feedbackService.getFeedbackForCourse(code);

        if(feedbacks.isEmpty()) {
            System.out.println("No feedback yet.");
            return;
        }

        for(Feedback<?> f : feedbacks) {
            System.out.println(f.toString());
        }
    }
    private void viewMyCourses() {
        List<String> courseCodes = professor.getAssignedCourses();

        if (courseCodes.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }

        for (String code : courseCodes) {
            Course c = courseRepository.getCourseByCode(code);
            if (c != null) {
                System.out.println("-----------------------------");
                System.out.println("Code    : " + c.getCourseCode());
                System.out.println("Title   : " + c.getTitle());
                System.out.println("Timings : " + c.getTimings());
                System.out.println("Location: " + c.getLocation());
                System.out.println("Credits : " + c.getCredits());
            }
        }
    }

    private void updateCourseDetails() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        Course course = courseRepository.getCourseByCode(code);

        if (course == null || !professor.getAssignedCourses().contains(code)) {
            System.out.println("Invalid course");
            return;
        }

        System.out.println("1. Update Title");
        System.out.println("2. Update Timings");
        System.out.println("3. Update Location");
        System.out.println("4. Update Syllabus");
        System.out.println("5. Update Credits");
        System.out.println("6. Update Enrollment Limit");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }

        switch (choice) {
            case 1:
                System.out.print("Enter new title: ");
                course.setTitle(scanner.nextLine());
                break;
            case 2:
                System.out.print("Enter new timings: ");
                course.setTimings(scanner.nextLine());
                break;
            case 3:
                System.out.print("Enter new location: ");
                course.setLocation(scanner.nextLine());
                break;
            case 4:
                System.out.print("Enter new syllabus: ");
                course.setSyllabus(scanner.nextLine());
                break;
            case 5:
                try {
                    System.out.print("Enter new credits: ");
                    course.setCredits(Integer.parseInt(scanner.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number");
                }
                break;
            case 6:
            	try {
                    System.out.print("Enter new limit: ");
                    int limit = Integer.parseInt(scanner.nextLine());
                    course.setSize(limit);
                } 
            	catch(NumberFormatException e) {
                    System.out.println("Invalid number");
                }
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        System.out.println("Course updated");
    }

    private void viewEnrolledStudents() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        Course course = courseRepository.getCourseByCode(code);

        if (course == null || !professor.getAssignedCourses().contains(code)) {
            System.out.println("Invalid course");
            return;
        }

        List<String> students = course.getEnrolledStudents();

        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        for (String studentId : students) {
            User user = userRepository.getUserById(studentId);
            if (user instanceof Student) {
                Student s = (Student) user;
                System.out.println("-----------------------------");
                System.out.println("Name    : " + s.getName());
                System.out.println("Email   : " + s.getEmail());
                System.out.println("Semester: " + s.getCurrSem());
            }
        }
    }
}