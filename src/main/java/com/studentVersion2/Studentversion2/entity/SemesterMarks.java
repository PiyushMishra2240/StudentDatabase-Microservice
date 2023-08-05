package com.studentVersion2.Studentversion2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class SemesterMarks {
    @Id
    @Column(name = "id")
    private int id;
    private List<Integer> size = new ArrayList<>();
    private List<Integer> semIds = new ArrayList<>();
    private List<String> subjects = new ArrayList<>();
    private List<Float> subMarks = new ArrayList<>();
}
