package com.university.model;

import java.time.LocalDate;
import java.util.*;

import com.university.enums.ComplaintStatus;

public class Complaint {

    private String complaintId;
    private String studentId;
    private String description;
    private ComplaintStatus status;
    private LocalDate dateSubmitted;
    private String resolutionDetails;


    public Complaint(String studentId, String description) {
        this.complaintId = UUID.randomUUID().toString(); 
        this.studentId = studentId;
        this.description = description;
        this.status = ComplaintStatus.PENDING;
        this.dateSubmitted = LocalDate.now();
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getDescription() {
        return description;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public LocalDate getDateSubmitted() {
        return dateSubmitted;
    }

    public String getResolutionDetails() {
        return resolutionDetails;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public void setResolutionDetails(String resolutionDetails) {
        this.resolutionDetails = resolutionDetails;
    }


    public void resolve(String resolutionDetails) {
        this.status = ComplaintStatus.RESOLVED;
        this.resolutionDetails = resolutionDetails;
    }

@Override
    public String toString() {
        return "Complaint{" + 
                "complaintId='" + complaintId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", dateSubmitted=" + dateSubmitted +
                ", resolutionDetails='" + resolutionDetails + '\'' +
                '}';
    }
}
