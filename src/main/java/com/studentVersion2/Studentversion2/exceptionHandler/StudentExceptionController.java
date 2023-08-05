package com.studentVersion2.Studentversion2.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionController {
    @ExceptionHandler(value = StudentIdNotfoundException.class)
    public ResponseEntity<Object> exception(StudentIdNotfoundException exception){
        return new ResponseEntity<>("Student ID is not present in database, please check the given ID", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = SemesterIdNotFoundException.class)
    public ResponseEntity<Object> exception(SemesterIdNotFoundException exception){
        return new ResponseEntity<>("Semester is wrong, please put correct semester!", HttpStatus.BAD_REQUEST);
    }
}
