/*
 * EduHistory 전역 커스텀 예외 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.common;

public class InvalidSearchConditionException extends RuntimeException {

    public InvalidSearchConditionException(String message) {

        super(message);
    }
}
