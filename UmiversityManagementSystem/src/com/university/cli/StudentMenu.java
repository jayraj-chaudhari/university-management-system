package com.university.cli;

import com.university.exception.CourseFullException;
import com.university.exception.DropDeadlinePassedException;
import com.university.model.*;
import com.university.service.*;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {

    private Student student;
    private EnrollmentService enrollmentService;
    private GradeService gradeService;
    private ComplaintService complaintService;
    private Scanner scanner;

    public StudentMenu(Student student, EnrollmentService enrollmentService,
                       GradeService gradeService, ComplaintService complaintService) {
        this.student = student;
        this.enrollmentService = enrollmentService;
        this.gradeService = gradeService;
        this.complaintService = complaintService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while(true) {
            System.out.println("\n===== STUDENT MENU =====");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View My Schedule");
            System.out.println("4. View Academic Progress");
            System.out.println("5. Drop Course");
            System.out.println("6. Submit Complaint");
            System.out.println("7. View My Complaints");
            System.out.println("8. View Portfolio");
            System.out.println("9. Give Course Feedback");
            System.out.println("0. Logout");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } 
            catch(NumberFormatException e) {
                System.out.println("Please enter a valid number");
                continue;
            }

            switch(choice) {
                case 1: viewAvailableCourses(); break;
                case 2: registerCourse(); break;
                case 3: viewSchedule(); break;
                case 4: viewAcademicProgress(); break;
                case 5: dropCourse(); break;
                case 6: submitComplaint(); break;
                case 7: viewComplaints(); break;
                case 8: viewPortFolio();break;
                case 9: giveFeedback();break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    private void giveFeedback() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        // Check student completed this course
        if(!student.getCompletedCourses().contains(code)) {
            System.out.println("You can only give feedback for completed courses");
            return;
        }
        FeedbackService feedbackService = new FeedbackService();
        System.out.println("Feedback Type:");
        System.out.println("1. Numeric Rating (1-5)");
        System.out.println("2. Text Comment");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch(NumberFormatException e) {
            System.out.println("Invalid input");
            return;
        }

        try {
			switch(choice) {
                case 1:
                    System.out.print("Enter Rating (1-5): ");
                    int rating = Integer.parseInt(scanner.nextLine());
                    feedbackService.submitRating(student.getUid(), code, rating);
                    System.out.println(" Rating submitted");
                    break;
                case 2:
                    System.out.print("Enter Comment: ");
                    String comment = scanner.nextLine();
                    feedbackService.submitComment(student.getUid(), code, comment);
                    System.out.println("Comment submitted");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
	private void viewPortFolio() {
    	student.displayDashboard();
    }
    private void viewAvailableCourses() {
        System.out.print("Enter Semester: ");
        int sem;
        try {
            sem = Integer.parseInt(scanner.nextLine());
        } 
        catch(NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return;
        }

        List<Course> courses = enrollmentService.getAvailableCourses(sem);

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course c : courses) {
            System.out.println(c.getCourseCode() + " | " + c.getTitle() + " | Credits: " + c.getCredits());
        }
    }

    private void registerCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        try {
            boolean success = enrollmentService.enrollStudent(student.getUid(), code);
            if(success) System.out.println("Enrolled successfully");
            else System.out.println("Enrollment failed");
        } 
        catch(CourseFullException e) {
        	e.getMessage();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewSchedule() {
        List<Course> courses = enrollmentService.getEnrolledCourses(student.getUid());

        if (courses.isEmpty()) {
            System.out.println("No enrolled courses.");
            return;
        }

        for(Course c : courses) {
            System.out.println("-----------------------------");
            System.out.println("Code    : " + c.getCourseCode());
            System.out.println("Title   : " + c.getTitle());
            System.out.println("Timings : " + c.getTimings());
            System.out.println("Location: " + c.getLocation());
            System.out.println("Credits : " + c.getCredits());
        }
    }
    
    private void viewAcademicProgress() {
        List<CourseRecord> records = gradeService.getStudentRecords(student.getUid());

        if(records.isEmpty()) {
            System.out.println("No completed courses yet.");
        } else {
            System.out.println("Completed Courses:");
            for(CourseRecord r : records) {
                System.out.println(r.getCourseCode() +
                        " | Sem: " + r.getSemester() +
                        " | Grade: " + r.getGrade() +
                        " | Credits: " + r.getCredits());
            }
        }

        System.out.print("Enter Semester for SGPA: ");
        int sem;
        try {
            sem = Integer.parseInt(scanner.nextLine());
        } 
        catch(NumberFormatException e) {
            System.out.println(" Please enter a valid number");
            return;
        }

        System.out.println("SGPA (Sem " + sem + "): " +
                gradeService.calculateSGPA(student.getUid(), sem));
        System.out.println("CGPA: " +
                gradeService.calculateCGPA(student.getUid()));
    }

    private void dropCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();

        try {
            boolean success = enrollmentService.dropCourse(student.getUid(), code);

            if (success) {
                System.out.println("Course dropped");
            } else {
                System.out.println("Drop failed");
            }
        }
        catch(DropDeadlinePassedException e) {
            System.out.println("❌ " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void submitComplaint() {
        System.out.print("Enter Complaint Description: ");
        String desc = scanner.nextLine();

        try {
            complaintService.submitComplaint(student.getUid(), desc);
            System.out.println("Complaint submitted");
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private void viewComplaints() {
        List<Complaint> complaints = complaintService.getComplaintsByStudent(student.getUid());

        if (complaints.isEmpty()) {
            System.out.println("No complaints found.");
            return;
        }

        for (Complaint c : complaints) {
            System.out.println(c.getComplaintId() + " | " + c.getStatus() + " | " + c.getDescription());
        }
    }
}