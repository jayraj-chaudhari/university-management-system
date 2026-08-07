package com.university.model;
import java.util.ArrayList;
import java.util.List;

import com.university.enums.Role;

public class Professor extends User {

    private List<String> assignedCourses; 
    private String officeHours;       
    private String expertise;         

    public Professor(String uid, String name, String email, String password, Role role) {
        super(uid, name, email, password, role);
        this.assignedCourses = new ArrayList<>();
        this.officeHours = "";
        this.expertise = "";
    }

    public List<String> getAssignedCourses() {
        return assignedCourses;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void addAssignedCourse(String courseCode) {
        if (courseCode != null && !courseCode.trim().isEmpty() && !assignedCourses.contains(courseCode)) {
            assignedCourses.add(courseCode);
        }
    }

    public void removeAssignedCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }

    @Override
    public void displayDashboard() {
        System.out.println("===== Professor Dashboard =====");
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Expertise: " + expertise);
        System.out.println("Office Hours: " + officeHours);
        System.out.println("Assigned Courses:");
        if (assignedCourses.isEmpty()) {
            System.out.println("  No courses assigned.");
        } else {
            for (String course : assignedCourses) {
                System.out.println("  - " + course);
            }
        }
    }
}
