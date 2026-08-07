package com.university.exception;

public class CourseFullException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public CourseFullException(String message) {
        super(message);
    }
}