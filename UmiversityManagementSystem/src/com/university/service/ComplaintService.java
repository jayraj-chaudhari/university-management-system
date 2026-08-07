package com.university.service;

import com.university.enums.ComplaintStatus;
import com.university.model.*;
import com.university.repository.*;

import java.util.ArrayList;
import java.util.List;

public class ComplaintService {

    private ComplaintRepository complaintRepository = ComplaintRepository.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();

    public ComplaintService() {}

    //SUBMIT COMPLAINT

    public Complaint submitComplaint(String studentId, String description) {

        //Validate student
        User user = userRepository.getUserById(studentId);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Invalid student");
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Complaint complaint = new Complaint(studentId, description);

        //Save
        complaintRepository.addComplaint(complaint);

        return complaint;
    }
    
    //Resolve complaint

    public boolean resolveComplaint(String complaintId, String resolutionDetails) {

        Complaint complaint = complaintRepository.getComplaintById(complaintId);

        if (complaint == null) {
            throw new IllegalArgumentException("Complaint not found");
        }

        if (resolutionDetails == null || resolutionDetails.trim().isEmpty()) {
            throw new IllegalArgumentException("Resolution details required");
        }

        //Use business method from model
        complaint.resolve(resolutionDetails);

        return true;
    }

    //get by student

    public List<Complaint> getComplaintsByStudent(String studentId) {
        return complaintRepository.getComplaintsByStudent(studentId);
    }


    public List<Complaint> getAllComplaints() {
        return complaintRepository.getAllComplaints();
    }

    //Filter by status

    public List<Complaint> filterByStatus(ComplaintStatus status) {

        List<Complaint> result = new ArrayList<>();

        for (Complaint c : complaintRepository.getAllComplaints()) {
            if (c.getStatus() == status) {
                result.add(c);
            }
        }

        return result;
    }
}