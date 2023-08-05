package com.studentVersion2.Studentversion2.repository;

import com.studentVersion2.Studentversion2.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    public Department findById(int id);
}
