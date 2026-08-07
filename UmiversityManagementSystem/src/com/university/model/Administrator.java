package com.university.model;

import com.university.enums.Role;

public class Administrator extends User{
	
	public static final String ADMIN_EMAIL = "admin@svnit.ac.in";
	public static final String ADMIN_PASSWORD= "800000";
	public static final String ADMIN_NAME = "admin";
	
	public Administrator(String uid) {
	    super(uid,ADMIN_NAME, ADMIN_EMAIL,ADMIN_PASSWORD,Role.ADMINISTRATOR);
	}
	@Override
	public void displayDashboard() {
		System.out.println("Name              :  "+getName());
		System.out.println("E-mail            : "+getEmail());
		System.out.println("Admin ID        :"+getUid());		
	}
}
