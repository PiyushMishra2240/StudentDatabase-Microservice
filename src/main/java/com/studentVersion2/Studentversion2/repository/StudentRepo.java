package com.studentVersion2.Studentversion2.repository;

import com.studentVersion2.Studentversion2.entity.Department;
import com.studentVersion2.Studentversion2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
}
