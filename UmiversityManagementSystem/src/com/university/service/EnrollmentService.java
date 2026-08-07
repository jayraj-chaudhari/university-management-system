package com.university.service;
import com.university.exception.CourseFullException;
import com.university.exception.DropDeadlinePassedException;
import com.university.model.*;
import com.university.repository.CourseRepository;
import com.university.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {

    private UserRepository userRepository = UserRepository.getInstance();
    private CourseRepository courseRepository = CourseRepository.getInstance();
    
    private static final LocalDate DROP_DEADLINE = LocalDate.of(2027, 01, 31);
    
    public EnrollmentService() {
        
    }
    private int getTotalCredits(Student student) {
        int total = 0;
        for(String code : student.getEnrolledCourses()) {
            Course c = courseRepository.getCourseByCode(code);
            if(c != null) total += c.getCredits();
        }
        return total;
    }
    // ENROLL STUDENT

    public boolean enrollStudent(String studentId, String courseCode) throws CourseFullException {

        User user = userRepository.getUserById(studentId);
        Course course = courseRepository.getCourseByCode(courseCode);
        

        // Validation
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }
        
        Student student = (Student) user;

        List<String> completed = student.getCompletedCourses();
        for(String prereq : course.getPrereq()) {
            if(!completed.contains(prereq)) {
                throw new IllegalArgumentException("Prerequisites not met: " + prereq);
            }
        }
        
        int currentCredits = getTotalCredits(student);
        if(currentCredits + course.getCredits() > 20) {
            throw new IllegalArgumentException("Credit limit exceeded. Max 20 credits.");
        }
        
        if(course.getSemester() != student.getCurrSem()) {
            throw new IllegalArgumentException("Course not available for your semester.");
        }
        // Already enrolled
        if (student.getEnrolledCourses().contains(courseCode)) {
            return false;
        }

        // Capacity check (if implemented in Course)
        if(course.isFull()) 
            throw new CourseFullException("Course " + courseCode + " is full");

        // Enroll both sides
        student.addEnrolledCourse(courseCode);
        course.addEnrolledStudent(studentId);

        return true;
    }

    // DROP COURSE

	public boolean dropCourse(String studentId, String courseCode) throws DropDeadlinePassedException {

        User user = userRepository.getUserById(studentId);
        Course course = courseRepository.getCourseByCode(courseCode);

        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        if (course == null) {
            throw new IllegalArgumentException("Course not found");
        }
        
        if(LocalDate.now().isAfter(DROP_DEADLINE)) {
            throw new DropDeadlinePassedException(
                "Drop deadline was " + DROP_DEADLINE + ". Cannot drop course.");
        }
        
        Student student = (Student) user;

        boolean removedFromStudent = student.getEnrolledCourses().remove(courseCode);
        boolean removedFromCourse = course.getEnrolledStudents().remove(studentId);
        return removedFromStudent && removedFromCourse;
    }

    // VIEW ENROLLED COURSES

    public List<Course> getEnrolledCourses(String studentId) {

        User user = userRepository.getUserById(studentId);

        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Invalid student ID");
        }

        Student student = (Student) user;

        List<Course> result = new ArrayList<>();

        for (String courseCode : student.getEnrolledCourses()) {
            Course course = courseRepository.getCourseByCode(courseCode);
            if (course != null) {
                result.add(course);
            }
        }

        return result;
    }

    // VIEW AVAILABLE COURSES (BY SEMESTER)

    public List<Course> getAvailableCourses(int semester) {
        return courseRepository.getCoursesBySemester(semester);
    }

    // CHECK ENROLLMENT

    public boolean isStudentEnrolled(String studentId, String courseCode) {

        User user = userRepository.getUserById(studentId);

        if (user == null || !(user instanceof Student)) {
            return false;
        }

        Student student = (Student) user;

        return student.getEnrolledCourses().contains(courseCode);
    }
}
