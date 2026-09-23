/*
 * EduHistory 검색 객체 VO 클래스
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
public class EduHistorySearchVO {

    /** 검색 구분: all / course / institution */
    private String searchType;

    /** 검색어 */
    private String keyword;

    /** 검색 시작일 (원본 입력값, YYYY-MM-DD) */
    private String startDate;

    /** 검색 종료일 (원본 입력값, YYYY-MM-DD) */
    private String endDate;

    /** 수강상태: 빈 값(전체) / 수료 / 수강 / 미수료 */
    private String status;

    /** 검증을 통과한 시작일 (SQL 바인딩용) */
    private LocalDate fromDate;

    /** 검증을 통과한 종료일 (SQL 바인딩용) */
    private LocalDate toDate;
}
