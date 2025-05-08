package com.example.ServerUniApp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbsenceStudentVO {
    private Integer studentId;
    private String studentNumber;
    private String studentName;
    private Integer absenceCount;
}
