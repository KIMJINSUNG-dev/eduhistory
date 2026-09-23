/*
 * EduHistory 데이터 객체 VO 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EduHistoryVO {

    private Long      id;
    private String    traineeName;
    private LocalDate eduStartDate;
    private LocalDate eduEndDate;
    private String    courseName;
    private String    institutionName;
    private String    courseStatus;
}
