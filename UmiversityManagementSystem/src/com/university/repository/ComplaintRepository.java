package com.university.repository;

import java.util.*;
import com.university.model.Complaint;

public class ComplaintRepository {

    private Map<String, Complaint> complaints;

    //Singleton instance 
    private static  ComplaintRepository instance;

    //Private constructor
    private ComplaintRepository() {
        this.complaints = new HashMap<>();
    }

    //Singleton
    public static ComplaintRepository getInstance() {
        if (instance == null) {
            instance = new ComplaintRepository();        
        }
        return instance;
    }



    public void addComplaint(Complaint complaint) {
        if (complaint == null || complaint.getComplaintId() == null) {
            throw new IllegalArgumentException("Complaint or ID cannot be null");
        }
        complaints.put(complaint.getComplaintId(), complaint);
    }

    public Complaint getComplaintById(String id) {
        return complaints.get(id);
    }

    public List<Complaint> getAllComplaints() {
        return new ArrayList<>(complaints.values());
    }

    public List<Complaint> getComplaintsByStudent(String studentId) {
        List<Complaint> result = new ArrayList<>();

        for (Complaint complaint : complaints.values()) {
            if (studentId.equals(complaint.getStudentId())) {
                result.add(complaint);
            }
        }
        return result;
    }
}