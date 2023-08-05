package com.studentVersion2.Studentversion2.service;

import com.studentVersion2.Studentversion2.dto.StudentFullResponse;
import com.studentVersion2.Studentversion2.dto.StudentResponse;
import com.studentVersion2.Studentversion2.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    public void saveStudents(MultipartFile file);
    public ResponseEntity<StudentResponse> getStudent(int id, int semId);
    public ResponseEntity<StudentFullResponse> getStudentDetails(int id);
    public void saveDepartment(MultipartFile file);
    public String saveSem(MultipartFile file);
}
