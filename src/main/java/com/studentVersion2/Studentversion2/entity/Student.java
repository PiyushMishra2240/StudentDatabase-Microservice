package com.studentVersion2.Studentversion2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="course")
    private String course;
    @Column(name="name")
    private String name;
    @Column(name = "totalSemesters")
    private int totalSemesters;
    @OneToOne(cascade = CascadeType.ALL)
    private Department department;
}
