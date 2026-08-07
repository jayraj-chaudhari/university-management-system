package com.university.model;
import java.util.UUID;

public class CourseRecord {

    private String recordId;
    private String studentId;
    private String courseCode;
    private double grade;
    private int credits;
    private int semester;

    public CourseRecord(String studentId, String courseCode, double grade, int credits, int semester) {
        this.recordId = UUID.randomUUID().toString();
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.grade = grade;
        this.credits = credits;
        this.semester = semester;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public double getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }

    public int getSemester() {
        return semester;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 10.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 10");
        }
        this.grade = grade;
    }

    public void setCredits(int credits) {
        if (credits < 0) {
            throw new IllegalArgumentException("Credits cannot be negative");
        }
        this.credits = credits;
    }

    public void setSemester(int semester) {
        if (semester <= 0) {
            throw new IllegalArgumentException("Semester must be positive");
        }
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "CourseRecord{" +
                "recordId='" + recordId +  
                ", studentId='" + studentId +
                ", courseCode='" + courseCode + 
                ", grade=" + grade +
                ", credits=" + credits +
                ", semester=" + semester +
                '}';
    }

}
