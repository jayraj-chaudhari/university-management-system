package com.university.repository;

import com.university.model.CourseRecord;
import java.util.*;

public class CourseRecordRepository {

    private Map<String, CourseRecord> records;

    //Singleton instance
    private static CourseRecordRepository instance;

    //Private constructor
    private CourseRecordRepository() {
        this.records = new HashMap<>();
    }

    public static CourseRecordRepository getInstance() {
        if (instance == null) {
        	instance = new CourseRecordRepository();
        }
        return instance;
    }


    // Add Record
    public void addRecord(CourseRecord record) {
        if (record == null || record.getRecordId() == null) {
            throw new IllegalArgumentException("Invalid record");
        }
        records.put(record.getRecordId(), record);
    }

    // Get All Records
    public List<CourseRecord> getAllRecords() {
        return new ArrayList<>(records.values());
    }

    // Get Records by Student
    public List<CourseRecord> getRecordsByStudent(String studentId) {
        List<CourseRecord> result = new ArrayList<>();

        for (CourseRecord r : records.values()) {
            if (studentId.equals(r.getStudentId())) {
                result.add(r);
            }
        }

        return result;
    }

    // Get Records by Student + Semester
    public List<CourseRecord> getRecordsByStudentAndSemester(String studentId, int semester) {
        List<CourseRecord> result = new ArrayList<>();

        for (CourseRecord r : records.values()) {
            if (studentId.equals(r.getStudentId()) && r.getSemester() == semester) {
                result.add(r);
            }
        }

        return result;
    }
}