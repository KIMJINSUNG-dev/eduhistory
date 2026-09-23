/*
 * EduHistory 검색어 타입 Enum
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.code;

public enum SearchType {

    ALL("all"),
    COURSE("course"),
    INSTITUTION("institution");

    private final String code;

    SearchType(String code) {

        this.code = code;
    }

    public String getCode() {

        return code;
    }

    public static boolean isSupported(String code) {

        for (SearchType type : values()) {

            if (type.code.equals(code)) return true;
        }

        return false;
    }
}