package com.studentVersion2.Studentversion2.repository;

import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemesterMarksRepo extends JpaRepository<SemesterMarks, Integer> {
    public SemesterMarks findById(int id);
}
