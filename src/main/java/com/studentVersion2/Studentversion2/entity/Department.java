package com.studentVersion2.Studentversion2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @Column(name="id")
    private int id;
    @Column(name = "DepartmentName")
    private String departmentName;
    @ElementCollection
    private Map<Integer, Integer> semWiseFullMarks = new HashMap<>();
    @OneToOne
    private SemesterMarks semesterMarks;
}
