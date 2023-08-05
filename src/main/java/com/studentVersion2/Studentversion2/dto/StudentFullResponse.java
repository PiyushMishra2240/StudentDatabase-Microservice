package com.studentVersion2.Studentversion2.dto;

import com.studentVersion2.Studentversion2.pairHelp.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentFullResponse {
    private int id;
    private String name;
    private String dept;
    private List<Details> fullDetails;
    private float fullMarks;
    private float totalObtainedMarks;
    private float overallPercentage;
}
