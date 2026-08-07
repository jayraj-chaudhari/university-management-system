package com.university.repository;

import com.university.model.Feedback;
import java.util.*;

public class FeedbackRepository {

    private static FeedbackRepository instance;
    private List<Feedback<?>> feedbacks;

    private FeedbackRepository() {
        this.feedbacks = new ArrayList<>();
    }

    public static FeedbackRepository getInstance() {
        if(instance == null) instance = new FeedbackRepository();
        return instance;
    }

    public void addFeedback(Feedback<?> feedback) {
        feedbacks.add(feedback);
    }

    public List<Feedback<?>> getAllFeedbacks() {
        return new ArrayList<>(feedbacks);
    }

    public List<Feedback<?>> getFeedbackByCourse(String courseCode) {
        List<Feedback<?>> result = new ArrayList<>();
        for(Feedback<?> f : feedbacks) {
            if(f.getCourseCode().equals(courseCode)) {
                result.add(f);
            }
        }
        return result;
    }

    public List<Feedback<?>> getFeedbackByStudent(String studentId) {
        List<Feedback<?>> result = new ArrayList<>();
        for(Feedback<?> f : feedbacks) {
            if(f.getStudentId().equals(studentId)) {
                result.add(f);
            }
        }
        return result;
    }
}