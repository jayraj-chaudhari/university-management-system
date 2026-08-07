package com.university.repository;
import java.util.*;
import com.university.model.User;

public class UserRepository {

    private Map<String, User> users;

    private static volatile UserRepository instance;

    private UserRepository() {
        this.users = new HashMap<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public void addUser(User user) {
        if (user == null || user.getUid() == null) {
            throw new IllegalArgumentException("User or UID cannot be null");
        }
        users.put(user.getUid(), user);
    }

    public User getUserById(String uid) {
        return users.get(uid);
    }

    public User getUserByEmail(String email) {
        if (email == null) return null;

        for (User user : users.values()) {
            if (email.equalsIgnoreCase(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void removeUser(String uid) {
        users.remove(uid);
    }
}