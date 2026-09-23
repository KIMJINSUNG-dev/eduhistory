/*
 * EduHistory 목록 응답 객체 반환을 위한 서비스 인터페이스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service;

public interface EduHistoryService {

    EduHistoryListResponse selectEduHistoryList(EduHistorySearchVO searchVO);
}
