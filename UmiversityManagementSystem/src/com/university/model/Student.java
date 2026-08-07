package com.university.model;

import java.util.ArrayList;
import java.util.List;

import com.university.enums.Role;

public class Student extends User 
{
	private int currSem;
	private List<String> enrolledCourses;
	private List<String> completedCourses;
	private List<Complaint> complaints;
	
	public Student(String uid, String name, String email, String password,Role role)
	{
		super(uid,name,email,password,role);
		this.enrolledCourses = new ArrayList<>();
		this.completedCourses = new ArrayList<>();
		this.complaints =  new ArrayList<>();
		this.currSem = 1;
	}

	public List<String> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void addCompletedCourse(String course) {
	    this.completedCourses.add(course);
	}

	public List<String> getCompletedCourses() {
		return completedCourses;
	}

	public void addEnrolledCourse(String course) {
	    this.enrolledCourses.add(course);
	}
	
	public List<Complaint> getComplaints() {
		return complaints;
	}
	public void addComplaint(Complaint complaint) {
	    this.complaints.add(complaint);
	}

	public int getCurrSem() {
		return currSem;
	}

	public void setCurrSem(int currSem) {
		this.currSem = currSem;
	}
	public void displayDashboard()
	{
		System.out.println("Name              :  "+super.getName());
		System.out.println("E-mail            :"+super.getEmail());
		System.out.println("Current Semester  :  "+this.currSem);
		System.out.println("Student ID        :"+super.getUid());
		System.out.println("Enrolled Courses:");
		if(enrolledCourses.isEmpty()) {
		    System.out.println("  No courses enrolled yet.");
		} else {
		    for(String courseCode : enrolledCourses) {
		        System.out.println("  - " + courseCode);
		    }
		}
		System.out.println("Completed Courses:");
		if(completedCourses.isEmpty()) {
		    System.out.println("  No courses completed yet.");
		} else {
		    for(String courseCode : completedCourses) {
		        System.out.println("  - " + courseCode);
		    }
		}
	}
}
