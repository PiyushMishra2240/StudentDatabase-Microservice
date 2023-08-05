package com.studentVersion2.Studentversion2.dto;

import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SemesterMarksResponse {
    private List<SemesterMarks> semesterMarksList = new ArrayList<>();
    private List<String> missingCells = new ArrayList<>();
}
