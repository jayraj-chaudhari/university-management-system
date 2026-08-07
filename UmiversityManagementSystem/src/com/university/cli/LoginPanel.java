package com.university.cli;

import com.university.enums.Role;
import com.university.exception.InvalidLoginException;
import com.university.model.*;
import com.university.service.*;

import java.util.Scanner;

public class LoginPanel {

    private AuthService authService;
    private EnrollmentService enrollmentService;
    private GradeService gradeService;
    private ComplaintService complaintService;
    private Scanner scanner;

    public LoginPanel(AuthService authService, EnrollmentService enrollmentService,GradeService gradeService, ComplaintService complaintService) {
        this.authService = authService;
        this.enrollmentService = enrollmentService;
        this.gradeService = gradeService;
        this.complaintService = complaintService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n===== SVNIT UNIVERSITY PORTAL =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }

            switch (choice) {
                case 1: handleLogin(); break;
                case 2: handleSignup(); break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void handleLogin() {
        
        User user = null;

        try {
        	System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            user = authService.login(email, password);
            System.out.println("Login successful! Welcome, " + user.getName());
            redirectToDashboard(user);
        } 
        catch(InvalidLoginException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleSignup() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        System.out.println("Select Role:");
        System.out.println("1. Student");
        System.out.println("2. Professor");
        System.out.println("3. Teaching Assistant");

        int roleChoice;
        try {
            roleChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }

        try {
            switch (roleChoice) {
                case 1:
                    authService.signUpStudent(name, email, password);
                    System.out.println("Student account created! Please login.");
                    break;
                case 2:
                    authService.signUpProfessor(name, email, password);
                    System.out.println("Professor account created! Please login.");
                    break;
                case 3:
                    authService.signUpTA(name, email, password);
                    System.out.println("TA account created!");
                    break;
                default:
                    System.out.println("Invalid role");
            }
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private void redirectToDashboard(User user) {
        Role role = user.getRole();
        switch (role) {
            case STUDENT:
                new StudentMenu((Student) user, enrollmentService,
                        gradeService, complaintService).start();
                break;
            case PROFESSOR:
                new ProfessorMenu((Professor) user).start();
                break;
            case ADMINISTRATOR:
                new AdministratorMenu().start();
                break;
            case TA:
                new TAMenu((TA) user, enrollmentService, 
                           gradeService, complaintService).start();
                break;
            default:
                System.out.println("Unknown role");
        }
    }
}