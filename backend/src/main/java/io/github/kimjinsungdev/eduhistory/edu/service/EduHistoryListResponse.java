/*
 * EduHistory 목록 응답 객체 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service;

import lombok.Getter;

import java.util.List;

@Getter
public class EduHistoryListResponse {

    private final int totalCount;
    private final List<EduHistoryVO> list;

    public EduHistoryListResponse(List<EduHistoryVO> list) {

        this.list       = list;
        this.totalCount = list.size();
    }
}
