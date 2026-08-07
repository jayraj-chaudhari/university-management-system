package com.university.cli;

import com.university.service.*;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        EnrollmentService enrollmentService = new EnrollmentService();
        GradeService gradeService = new GradeService();
        ComplaintService complaintService = new ComplaintService();

        LoginPanel loginPanel = new LoginPanel(authService, enrollmentService, gradeService, complaintService);
        loginPanel.start();
    }
}