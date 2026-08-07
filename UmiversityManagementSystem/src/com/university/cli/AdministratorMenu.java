package com.university.cli;

import com.university.enums.ComplaintStatus;
import com.university.model.*;
import com.university.repository.*;
import com.university.service.*;

import java.util.List;
import java.util.Scanner;

public class AdministratorMenu {

    private CourseRepository courseRepository = CourseRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();
    private ComplaintService complaintService = new ComplaintService();
    private GradeService gradeService = new GradeService();

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Manage Course Catalog");
            System.out.println("2. Manage Student Records");
            System.out.println("3. Assign Professor to Course");
            System.out.println("4. Handle Complaints");
            System.out.println("5. View All Professors");
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
                case 1: manageCourseCatalog(); break;
                case 2: manageStudentRecords(); break;
                case 3: assignProfessorToCourse(); break;
                case 4: handleComplaints(); break;
                case 5: viewAllProfessors(); break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    private void viewAllProfessors() {
        System.out.println("\n--- All Professors ---");
        boolean found = false;
        
        for(User u : userRepository.getAllUsers()) {
            if(u instanceof Professor) {
                Professor p = (Professor) u;
                System.out.println("======================");
                System.out.println("ID      : " + p.getUid());
                System.out.println("Name    : " + p.getName());
                System.out.println("Email   : " + p.getEmail());
                System.out.println("Expertise: " + p.getExpertise());
                System.out.println("Courses : " + p.getAssignedCourses());
                found = true;
            }
        }
        
        if(!found) {
            System.out.println("No professors registered yet.");
        }
    }
    private void manageCourseCatalog() {
        System.out.println("\n--- Course Catalog ---");
        System.out.println("1. View All Courses");
        System.out.println("2. Add Course");
        System.out.println("3. Delete Course");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } 
        catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        switch (choice) {
            case 1:
                List<Course> courses = courseRepository.getAllCourses();
                for (Course c : courses){
                    System.out.println(c.getCourseCode() + " | " + c.getTitle());
                }
                break;

            case 2:
            	System.out.print("Code: ");
            	String code = scanner.nextLine();
            	System.out.print("Title: ");
            	String title = scanner.nextLine();
            	System.out.print("Semester: ");
            	int sem = Integer.parseInt(scanner.nextLine());
            	System.out.print("Credits: ");
            	int credits = Integer.parseInt(scanner.nextLine());
            	System.out.print("Enter Capacity: ");
            	int size = Integer.parseInt(scanner.nextLine());
            	System.out.print("Professer ID: ");
            	String profId = scanner.nextLine();
            	Course c = new Course(code, title, credits, sem, size, profId);

            	courseRepository.addCourse(c);
            	System.out.println("Course added");
                break;

            case 3:
                System.out.print("Enter Course Code: ");
                String delCode = scanner.nextLine();
                if(courseRepository.getCourseByCode(delCode) == null) {
                    System.out.println("Course not found");
                } 
                else {
                    courseRepository.removeCourse(delCode);
                    System.out.println("Course removed");
                }
                break;
        }
    }

    private void manageStudentRecords() {
        System.out.println("\n--- Student Records ---");
        System.out.println("1. View All Students");
        System.out.println("2. View Student by ID");
        System.out.println("3. Assign Grade to Student");
        System.out.println("4. Update Student Info");

        int choice;
        try{
            choice = Integer.parseInt(scanner.nextLine());
        } 
        catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        switch (choice) {
            case 1:
                for (User u : userRepository.getAllUsers()) {
                    if (u instanceof Student) {
                        Student s = (Student) u;
                        System.out.println(s.getUid() + " | " + s.getName() + " | Semester:" + s.getCurrSem()+ " | E-mail:"+ s.getEmail());
                    }
                }
                break;

            case 2:
                System.out.print("Enter Student ID: ");
                User u = userRepository.getUserById(scanner.nextLine());
                if (u instanceof Student) {
                    Student s = (Student) u;
                    System.out.println(s.getName() + " | " + s.getEmail());
                } else {
                    System.out.println(" Not found");
                }
                break;

            case 3:
                try {
                    System.out.print("Student ID: ");
                    String sid = scanner.nextLine();
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Grade: ");
                    double grade = Double.parseDouble(scanner.nextLine());

                    gradeService.assignGrade(sid, code, grade);

                    User user = userRepository.getUserById(sid);
                    if(user instanceof Student) {
                        Student s = (Student) user;
                        if(s.getEnrolledCourses().isEmpty()) {
                            s.setCurrSem(s.getCurrSem() + 1);
                            System.out.println("Semester completed! Student moved to semester " + s.getCurrSem());
                        }
                    }

                    System.out.println("Grade assigned");
                } 
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 4:
                System.out.print("Enter Student ID: ");
                User user = userRepository.getUserById(scanner.nextLine());
                if (user instanceof Student) {
                    Student s = (Student) user;
                    System.out.print("New Name: ");
                    s.setName(scanner.nextLine());
                    System.out.print("New Email: ");
                    s.setEmail(scanner.nextLine());
                    System.out.println("Updated");
                } else {
                    System.out.println("Not found");
                }
                break;
        }
    }

    private void assignProfessorToCourse() {
        System.out.print("Enter Professor Email ID: ");
        String mail = scanner.nextLine();

        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        User u = userRepository.getUserByEmail(mail);
        Course c = courseRepository.getCourseByCode(code);

        if (!(u instanceof Professor) || c == null) {
            System.out.println("Invalid data");
            return;
        }

        Professor p = (Professor) u;
        p.addAssignedCourse(code);

        System.out.println("Assigned successfully");
    }

    private void handleComplaints() {
        System.out.println("\n--- Complaints ---");
        System.out.println("1. View All Complaints");
        System.out.println("2. Filter by Status");
        System.out.println("3. Resolve Complaint");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input");
            return;
        }

        switch (choice) {
            case 1:
                for (Complaint c : complaintService.getAllComplaints()) {
                    System.out.println(c.getComplaintId() + " | " + c.getStatus());
                }
                break;

            case 2:
                try {
                    System.out.println("Enter Status (PENDING/RESOLVED): ");
                    ComplaintStatus status = ComplaintStatus.valueOf(scanner.nextLine().toUpperCase());
                    for(Complaint c : complaintService.filterByStatus(status)) {
                        System.out.println(c.getComplaintId() + " | " + c.getStatus() + " | " + c.getDescription());
                    }
                } catch(IllegalArgumentException e) {
                    System.out.println("Invalid status");
                }
                break;

            case 3:
                System.out.print("Complaint ID: ");
                String id = scanner.nextLine();
                System.out.print("Resolution: ");
                String res = scanner.nextLine();
                complaintService.resolveComplaint(id, res);
                System.out.println("Resolved");
                break;
        }
    }
}