package com.university.service;

import com.university.model.*;
import com.university.repository.FeedbackRepository;

import java.util.List;

public class FeedbackService {

    private FeedbackRepository feedbackRepository = FeedbackRepository.getInstance();

    //Submit numeric rating (1-5)
    public void submitRating(String studentId,String courseCode, int rating) {
        if(rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        Feedback<Integer> feedback =  new Feedback<>(studentId, courseCode, rating);
        feedbackRepository.addFeedback(feedback);
    }

    //Submit comment
    public void submitComment(String studentId, String courseCode, String comment) {
        if(comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        Feedback<String> feedback = new Feedback<>(studentId, courseCode, comment);
        feedbackRepository.addFeedback(feedback);
    }

    // Get all feedback for a course
    public List<Feedback<?>> getFeedbackForCourse(String courseCode) {
        return feedbackRepository.getFeedbackByCourse(courseCode);
    }

    // Get all feedback by a student
    public List<Feedback<?>> getFeedbackByStudent(String studentId) {
        return feedbackRepository.getFeedbackByStudent(studentId);
    }
}