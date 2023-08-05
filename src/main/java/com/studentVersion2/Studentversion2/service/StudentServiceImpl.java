package com.studentVersion2.Studentversion2.service;

import com.studentVersion2.Studentversion2.dto.Details;
import com.studentVersion2.Studentversion2.dto.SemesterMarksResponse;
import com.studentVersion2.Studentversion2.dto.StudentFullResponse;
import com.studentVersion2.Studentversion2.dto.StudentResponse;
import com.studentVersion2.Studentversion2.entity.Department;
import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import com.studentVersion2.Studentversion2.entity.Student;
import com.studentVersion2.Studentversion2.exceptionHandler.SemesterIdNotFoundException;
import com.studentVersion2.Studentversion2.exceptionHandler.StudentIdNotfoundException;
import com.studentVersion2.Studentversion2.helper.DepartmentExcel;
import com.studentVersion2.Studentversion2.helper.SemesterExcel;
import com.studentVersion2.Studentversion2.helper.StudentExcel;
import com.studentVersion2.Studentversion2.pairHelp.Pair;
import com.studentVersion2.Studentversion2.repository.DepartmentRepo;
import com.studentVersion2.Studentversion2.repository.SemesterMarksRepo;
import com.studentVersion2.Studentversion2.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    SemesterMarksRepo semesterMarksRepo;
    @Override
    public void saveStudents(MultipartFile file) {
        try {
            StudentExcel studentExcel = new StudentExcel();
            List<Student> students = studentExcel.converterStudent(file.getInputStream());
            studentRepo.saveAll(students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDepartment(MultipartFile file) {  //invalid data and handle duplicate values -> done
        try{
            DepartmentExcel departmentExcel = new DepartmentExcel();
            List<Department> department = departmentExcel.converterDept(file.getInputStream());
            for(int i=0;i<department.size();i++){   //apply try catch
                Optional<Student> student = studentRepo.findById(department.get(i).getId());
                SemesterMarks semesterMarks = semesterMarksRepo.findById(department.get(i).getId());
                department.get(i).setSemesterMarks(semesterMarks);
                Optional<Student> student1 = studentRepo.findById(department.get(i).getId());
                studentRepo.deleteById(student.get().getId());
                student1.get().setDepartment(department.get(i));
                studentRepo.save(student1.get());
            }
            departmentRepo.saveAll(department);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<StudentResponse> getStudent(int id, int semId) {//implement one more api which takes just student id and displays the full details across all semesters -> done
        Optional<Student> student = studentRepo.findById(id);
        if(student.isEmpty()) throw new StudentIdNotfoundException();
        Map<String, Float> marks = new HashMap<>();
        int totalMarks = 0;
        try {
            totalMarks =  student.get().getDepartment().getSemWiseFullMarks().get(semId);
        }catch (Exception e){
            throw new SemesterIdNotFoundException();
        }
        float marksObtained = 0;
        SemesterMarks semesterMarks = student.get().getDepartment().getSemesterMarks();
        List<String> subjects = semesterMarks.getSubjects();
        List<Float> subMarks = semesterMarks.getSubMarks();
        List<Integer> size = semesterMarks.getSize();
        int cnt = size.get(semId - 1);
        int start = cnt * (semId - 1);
        while (cnt > 0 && start < subjects.size()) {
            marks.put(subjects.get(start), subMarks.get(start));
            marksObtained += subMarks.get(start);
            start += 1;
            cnt -= 1;
        }
        StudentResponse studentResponse = new StudentResponse(id, student.get().getName(), student.get().getCourse(), semId, marks, marksObtained, totalMarks, 0);
        studentResponse.calPercentage();
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);

        //handle the error and show a message to the user instead of the server error -> done
    }

    @Override
    public ResponseEntity<StudentFullResponse> getStudentDetails(int id) {
        Optional<Student> student = studentRepo.findById(id);
        if(student.isEmpty()) throw new StudentIdNotfoundException();
        List<Details> fullDetails = new ArrayList<>();
        Map<Integer, Integer> semWiseFullMarks = student.get().getDepartment().getSemWiseFullMarks();
        float fullMarks = 0;
        float totalMarksObtained = 0;
        float overallPercentage = 0;
        for(Integer semester: semWiseFullMarks.keySet()){
            Map<String, Float> marks = new HashMap<>();
            int totalMarks = semWiseFullMarks.get(semester);
            fullMarks += totalMarks;
            float marksObtained = 0;
            float percentage = 0;
            SemesterMarks semesterMarks = student.get().getDepartment().getSemesterMarks();
            List<String> subjects = semesterMarks.getSubjects();
            List<Float> subMarks = semesterMarks.getSubMarks();
            List<Integer> size = semesterMarks.getSize();
            int cnt = size.get(semester - 1);
            int start = cnt * (semester - 1);
            while (cnt > 0 && start < subjects.size()) {
                marks.put(subjects.get(start), subMarks.get(start));
                marksObtained += subMarks.get(start);
                start += 1;
                cnt -= 1;
            }
            percentage = marksObtained/totalMarks * 100;
            totalMarksObtained += marksObtained;
            Details details = new Details(semester, marks, marksObtained, totalMarks, percentage);
            fullDetails.add(details);
        }
        overallPercentage = totalMarksObtained/fullMarks * 100;
        StudentFullResponse studentFullResponse = new StudentFullResponse(student.get().getId(), student.get().getName(), student.get().getCourse(), fullDetails, fullMarks, totalMarksObtained, overallPercentage);
        return new ResponseEntity<>(studentFullResponse, HttpStatus.OK);
    }

    @Override
    public String saveSem(MultipartFile file){
        try{
            SemesterExcel semesterExcel = new SemesterExcel();
            SemesterMarksResponse semesterMarksResponse = semesterExcel.converterStudent(file.getInputStream());
            semesterMarksRepo.saveAll(semesterMarksResponse.getSemesterMarksList());
            String missing= "";
            if(!semesterMarksResponse.getMissingCells().isEmpty()){
                missing = "These are the missing cells: ";
                List<String> missingCells = semesterMarksResponse.getMissingCells();
                for(int i=0;i<missingCells.size();i++) {
                    missing += missingCells.get(i);
                    missing += " ";
                }
                missing += "\nPlease update the sheet, rest all the values are saved into DB";
            }
            if(missing.isEmpty()) missing = "Data saved into DB";
            return missing;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
