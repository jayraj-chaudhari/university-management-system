package com.university.model;

import com.university.enums.Role;
import com.university.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class TA extends Student {

    private String assignedProfessorId;
    private List<String> assistingCourses;

    public TA(String uid, String name, String email, String password) {
        super(uid, name, email, password, Role.TA);
        this.assistingCourses = new ArrayList<>();
        this.assignedProfessorId = "";
    }

    public String getAssignedProfessorId() {
        return assignedProfessorId;
    }

    public void setAssignedProfessorId(String professorId) {
        this.assignedProfessorId = professorId;
    }

    public List<String> getAssistingCourses() {
        return assistingCourses;
    }

    public void addAssistingCourse(String courseCode) {
        if(!assistingCourses.contains(courseCode)) {
            assistingCourses.add(courseCode);
        }
    }

    // View students enrolled in a course
    public List<String> viewEnrolledStudents(String courseCode) {
        Course course = CourseRepository.getInstance()
                                        .getCourseByCode(courseCode);
        if(course == null) return new ArrayList<>();
        return course.getEnrolledStudents();
    }

    // Assist grading — can suggest grade but not finalize
    public void assistGrading(String studentId, 
                               String courseCode, double grade) {
        System.out.println("TA Grade Suggestion → Student: " + studentId +
                " | Course: " + courseCode + 
                " | Suggested Grade: " + grade);
    }

    @Override
    public void displayDashboard() {
        System.out.println("===== TA DASHBOARD =====");
        System.out.println("Name     : " + getName());
        System.out.println("Email    : " + getEmail());
        System.out.println("UID      : " + getUid());
        System.out.println("Semester : " + getCurrSem());
        System.out.println("Assisting Courses: " + assistingCourses);
    }
}