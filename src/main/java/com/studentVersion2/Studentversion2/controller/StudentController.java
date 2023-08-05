package com.studentVersion2.Studentversion2.controller;

import com.studentVersion2.Studentversion2.dto.GetFullDetails;
import com.studentVersion2.Studentversion2.dto.GetStudent;
import com.studentVersion2.Studentversion2.dto.StudentFullResponse;
import com.studentVersion2.Studentversion2.dto.StudentResponse;
import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import com.studentVersion2.Studentversion2.helper.DepartmentExcel;
import com.studentVersion2.Studentversion2.helper.SemesterExcel;
import com.studentVersion2.Studentversion2.helper.StudentExcel;
import com.studentVersion2.Studentversion2.repository.SemesterMarksRepo;
import com.studentVersion2.Studentversion2.service.StudentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    SemesterMarksRepo semesterMarksRepo;

    @PostMapping("/getStudent")
    public ResponseEntity<StudentResponse> getStudent(@RequestBody GetStudent getStudent){
        int iid = Integer.parseInt(getStudent.getId());
        int semIid = Integer.parseInt(getStudent.getSemId());
        return studentService.getStudent(iid, semIid);
    }

    @PostMapping("/getFullDetails")
    public ResponseEntity<StudentFullResponse> getFullDetails(@RequestBody GetFullDetails getFullDetails){
        int id = Integer.parseInt(getFullDetails.getId());
        return studentService.getStudentDetails(id);
    }

    @PostMapping("/uploadData/stu")
    public ResponseEntity<String> upload(@RequestParam("fileStudent") MultipartFile fileStudent){
        StudentExcel studentExcel = new StudentExcel();
        if(studentExcel.isExcelStudent(fileStudent)){
            studentService.saveStudents(fileStudent);
            return ResponseEntity.status(HttpStatus.OK).body("Data saved into DB");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Excel file!");
    }

    @PostMapping("/uploadData/dept")
    public ResponseEntity<String> uploadDept(@RequestParam("fileDepartment") MultipartFile file){
        DepartmentExcel departmentExcel = new DepartmentExcel();
        if(departmentExcel.isExcelDept(file)){
            studentService.saveDepartment(file);
            return ResponseEntity.status(HttpStatus.OK).body("Data saved into DB");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Excel file!");
    }

    @PostMapping("/uploadData/sem")
    public String saveSem(@RequestParam("fileSem") MultipartFile fileSem){
        SemesterExcel semesterExcel = new SemesterExcel();
        if(semesterExcel.isExcelStudent(fileSem)){
            return studentService.saveSem(fileSem);
        }
        return "Error";
    }
}




/*
*
* {
    "id": "1",
    "course": "CSE",
    "name": "Piyush",
    "courseFullMarks": 100.0,
    "marksObtained": 70.0
}
* */
