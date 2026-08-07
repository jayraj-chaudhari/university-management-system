package com.university.model;

public class Feedback<T> {
    private String feedbackId;
    private String studentId;
    private String courseCode;
    private T value;

    public Feedback(String studentId, String courseCode, T value) {
        this.feedbackId = java.util.UUID.randomUUID().toString();
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.value = value;
    }

    public String getFeedbackId() { return feedbackId; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public T getValue() { return value; }

    @Override
    public String toString() {
        return "Course: " + courseCode + 
               " | Feedback: " + value;
    }
}