package com.university.repository;
import java.util.*;

import com.university.model.Course;

public class CourseRepository {

    private Map<String, Course> courses;

    private static CourseRepository instance;

    private CourseRepository() {
        this.courses = new HashMap<>();
    }

    public static CourseRepository getInstance() {
        if (instance == null) {
        	instance = new CourseRepository();    
        }
        return instance;
    }

    public void addCourse(Course course) {
        if (course == null || course.getCourseCode() == null) {
            throw new IllegalArgumentException("Course or courseCode cannot be null");
        }
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourseByCode(String code) {
        return courses.get(code);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values()); 
    }

    public void removeCourse(String code) {
        courses.remove(code);
    }

    public List<Course> getCoursesBySemester(int semester) {
        List<Course> result = new ArrayList<>();

        for (Course course : courses.values()) 
        {
            if (course.getSemester() == semester) 
            {
                result.add(course);
            }
        }
        return result;
    }
}