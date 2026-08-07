package com.university.cli;

import com.university.model.*;
import com.university.repository.*;
import com.university.service.*;
import java.util.List;
import java.util.Scanner;

public class TAMenu {

    private TA ta;
    private EnrollmentService enrollmentService;
    private GradeService gradeService;
    private ComplaintService complaintService;
    private UserRepository userRepository 
        = UserRepository.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public TAMenu(TA ta, EnrollmentService enrollmentService,GradeService gradeService, ComplaintService complaintService) {
        this.ta = ta;
        this.enrollmentService = enrollmentService;
        this.gradeService = gradeService;
        this.complaintService = complaintService;
    }

    public void start() {
        while(true) {
            System.out.println("\n===== TA MENU =====");
            System.out.println("--- Student Features ---");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View My Schedule");
            System.out.println("4. View Academic Progress");
            System.out.println("5. Drop Course");
            System.out.println("6. Submit Complaint");
            System.out.println("--- TA Features ---");
            System.out.println("7. View Enrolled Students");
            System.out.println("8. Suggest Grade");
            System.out.println("0. Logout");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } 
            catch(NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }

            switch(choice) {
                case 1: viewAvailableCourses(); break;
                case 2: registerCourse(); break;
                case 3: viewSchedule(); break;
                case 4: viewAcademicProgress(); break;
                case 5: dropCourse(); break;
                case 6: submitComplaint(); break;
                case 7: viewEnrolledStudents(); break;
                case 8: suggestGrade(); break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // Student features
    private void viewAvailableCourses() {
        List<Course> courses = enrollmentService
            .getAvailableCourses(ta.getCurrSem());
        if(courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for(Course c : courses) {
            System.out.println(c.getCourseCode() + 
                " | " + c.getTitle() + 
                " | Credits: " + c.getCredits());
        }
    }

    private void registerCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        try {
            enrollmentService.enrollStudent(ta.getUid(), code);
            System.out.println("Enrolled successfully");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewSchedule() {
        List<Course> courses = enrollmentService
            .getEnrolledCourses(ta.getUid());
        if(courses.isEmpty()) {
            System.out.println("No enrolled courses.");
            return;
        }
        for(Course c : courses) {
            System.out.println(c.getCourseCode() + 
                " | " + c.getTimings() + 
                " | " + c.getLocation());
        }
    }

    private void viewAcademicProgress() {
        System.out.print("Enter Semester for SGPA: ");
        try {
            int sem = Integer.parseInt(scanner.nextLine());
            System.out.println("SGPA: " + 
                gradeService.calculateSGPA(ta.getUid(), sem));
            System.out.println("CGPA: " + 
                gradeService.calculateCGPA(ta.getUid()));
        } 
        catch(NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    private void dropCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        try {
            enrollmentService.dropCourse(ta.getUid(), code);
            System.out.println("Course dropped");
        } catch(Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private void submitComplaint() {
        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        try {
            complaintService.submitComplaint(ta.getUid(), desc);
            System.out.println("Complaint submitted");
        } catch(Exception e) {
            System.out.println( e.getMessage());
        }
    }

    // TA specific features
    private void viewEnrolledStudents() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        if(!ta.getAssistingCourses().contains(code)) {
            System.out.println("You are not assisting this course");
            return;
        }

        List<String> students = ta.viewEnrolledStudents(code);
        if(students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        for(String sid : students) {
            User u = userRepository.getUserById(sid);
            if(u != null) {
                System.out.println(u.getUid() + 
                    " | " + u.getName() + 
                    " | " + u.getEmail());
            }
        }
    }

    private void suggestGrade() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        if(!ta.getAssistingCourses().contains(code)) {
            System.out.println("You are not assisting this course");
            return;
        }

        System.out.print("Enter Student ID: ");
        String sid = scanner.nextLine();
        System.out.print("Enter Suggested Grade: ");
        try {
            double grade = Double.parseDouble(scanner.nextLine());
            ta.assistGrading(sid, code, grade);
        } 
        catch(NumberFormatException e) {
            System.out.println("Invalid grade");
        }
    }
}