package com.university.exception;


public class DropDeadlinePassedException extends Exception {
  
	private static final long serialVersionUID = 1L;

	public DropDeadlinePassedException(String message) {
        super(message);
    }
}
