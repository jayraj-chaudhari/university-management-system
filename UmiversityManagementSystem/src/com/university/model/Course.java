package com.university.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private int semester;
    private int size;
    private String timings;
    private String location;
    private String syllabus;
    private String profId;
    private List<String> prereq;           //Course Codes
    private List<String> enrolledStudents; //Student IDs
    
    public Course(String courseCode, String title, int credits, int semester, int size, String profId) {

        if (credits != 2 && credits != 4) {
            throw new IllegalArgumentException();
        }
        this.profId = profId;
        this.credits = credits;
        this.courseCode = courseCode;
        this.title = title;
        this.semester = semester;
        this.size = size;
        prereq = new ArrayList<String>();
        enrolledStudents = new ArrayList<String>();
        this.timings = "";
        this.location = "";
        this.syllabus = "";
    }
    public void setSize(int size) {
    	this.size= size;
    }
    public String getProfessorId() {
        return profId;
    }

    public void setProfessorId(String professorId) {
        this.profId = professorId;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getTitle() {
        return title;
    }
    public int getSize() {
        return size;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCredits() {
        return credits;
    }
    public int getSemester() {
        return semester;
    }
    public void setCredits(int credits) {
        if (credits != 2 && credits != 4) {
            throw new IllegalArgumentException("Credits must be 2 or 4");
        }
        this.credits = credits;
    }
    public List<String> getPrereq() {
        return prereq;
    }
    public void addPreReq(String prereq) {
        this.prereq.add(prereq);
    }
    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }
    public void addEnrolledStudent(String enrolledStudents) {
        if (isFull()) {
            System.out.println("Course can't have more students.");
            return;
        }
        this.enrolledStudents.add(enrolledStudents); 
    }
    public boolean isFull() {
        return enrolledStudents.size() >= size;
    }
    public String getTimings() {
        return timings;
    }
    public void setTimings(String timings) {
        this.timings = timings;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getSyllabus() {
        return syllabus;
    }
    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
    public boolean dropStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }
}