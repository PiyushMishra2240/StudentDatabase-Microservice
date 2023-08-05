package com.studentVersion2.Studentversion2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Details {
    private int semester;
    private Map<String, Float> marks;
    private float marksObtained;
    private float totalMarks;
    private float percentage;
}
