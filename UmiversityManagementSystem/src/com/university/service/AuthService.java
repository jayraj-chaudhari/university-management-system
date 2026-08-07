package com.university.service;

import com.university.enums.Role;
import com.university.exception.InvalidLoginException;
import com.university.model.*;
import com.university.repository.UserRepository;

import java.util.UUID;

public class AuthService {

    private UserRepository userRepository = UserRepository.getInstance();

    public AuthService() {
    	 userRepository.addUser(new Administrator("ADMIN-001"));
    }
    // LOGIN
    public User login(String email, String password) throws InvalidLoginException {
        if(email == null || password == null) 
            throw new InvalidLoginException("Email and password cannot be empty");
        
        User user = userRepository.getUserByEmail(email);
        if(user == null) 
            throw new InvalidLoginException("No account found with this email");
        
        if(!user.checkPassword(password)) 
            throw new InvalidLoginException("Incorrect password");
        
        return user;
    }
    

    public Student signUpStudent(String name, String email, String password) {

        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        String uid = UUID.randomUUID().toString();

        Student student = new Student(
                uid,
                name,
                email,
                password,
                Role.STUDENT
        );

        userRepository.addUser(student);

        return student;
    }
    public TA signUpTA(String name, String email, String password) {
        if(isEmailTaken(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        String uid = "TA-" + UUID.randomUUID().toString().substring(0, 8);
        TA ta = new TA(uid, name, email, password);
        userRepository.addUser(ta);
        return ta;
    }

    public Professor signUpProfessor(String name, String email, String password) {

        if (isEmailTaken(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        String uid = UUID.randomUUID().toString();

        Professor professor = new Professor(
                uid,
                name,
                email,
                password,
                Role.PROFESSOR
        );

        userRepository.addUser(professor);

        return professor;
    }


    public boolean isEmailTaken(String email) {
        if (email == null) return false;
        return userRepository.getUserByEmail(email) != null;
    }
}