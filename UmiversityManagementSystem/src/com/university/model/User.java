package com.university.model;

import com.university.enums.Role;

abstract public class User 
{
	private String name;
	private String email;
	private String password;
	private String uid;
	private Role role;
	
	public User(String uid, String name, String email, String password,Role role) {
	    this.uid = uid;
	    this.name = name;
	    this.email = email;
	    this.password = password;
	    this.role = role;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	public String getEmail() {
		return this.email;
	}
	public void setPassword(String pass) {
		this.password = pass;
	}
	public boolean checkPassword(String password) {
		if(this.password.equals(password)) {
			return true;
		}
		return false;
	}
	public String getUid() {
		return this.uid;
	}
	
	public void setUid(String uid) {
		this.uid=uid;
	}
	public Role getRole() {
		return this.role;
	}
	
	public abstract void displayDashboard();
}
