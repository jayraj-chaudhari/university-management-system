package com.university.service;

import com.university.model.*;
import com.university.repository.*;

import java.util.List;

public class GradeService {

    private UserRepository userRepository = UserRepository.getInstance();
    private CourseRepository courseRepository = CourseRepository.getInstance();
    private CourseRecordRepository recordRepository = CourseRecordRepository.getInstance();

    public GradeService() {
    }

    public boolean assignGrade(String studentId, String courseCode, double grade) {

        //Validate student
        User user = userRepository.getUserById(studentId);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Invalid student");
        }

        //Validate course
        Course course = courseRepository.getCourseByCode(courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }

        Student student = (Student) user;

        //Validate enrollment
        if (!student.getEnrolledCourses().contains(courseCode)) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }

        //Create CourseRecord
        CourseRecord record = new CourseRecord(studentId, courseCode, grade,course.getCredits(), course.getSemester()
        );

        //Save record
        recordRepository.addRecord(record);

        //Remove from enrolled courses
        student.getEnrolledCourses().remove(courseCode);
        course.dropStudent(studentId);

        //track completed courses
        student.addCompletedCourse(courseCode);

        return true;
    }

    //calculate SGPA

    public double calculateSGPA(String studentId, int semester) {

        List<CourseRecord> records =recordRepository.getRecordsByStudentAndSemester(studentId, semester);

        double totalWeighted = 0.0;
        int totalCredits = 0;

        for (CourseRecord r : records) {
            totalWeighted += r.getGrade() * r.getCredits();
            totalCredits += r.getCredits();
        }

        if (totalCredits == 0) return 0.0;

        return totalWeighted / totalCredits;
    }

    //calculate CGPA

    public double calculateCGPA(String studentId) {

        List<CourseRecord> records =
                recordRepository.getRecordsByStudent(studentId);

        double totalWeighted = 0.0;
        int totalCredits = 0;

        for (CourseRecord r : records) {
            totalWeighted += r.getGrade() * r.getCredits();
            totalCredits += r.getCredits();
        }

        if (totalCredits == 0) return 0.0;

        return totalWeighted / totalCredits;
    }
    public List<CourseRecord> getStudentRecords(String studentId) {
        return recordRepository.getRecordsByStudent(studentId);
    }
}
