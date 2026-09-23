/*
 * EduHistory 수강상태 Enum
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.code;

public enum CourseStatus {

    COMPLETED("수료"),
    IN_PROGRESS("수강"),
    NOT_COMPLETED("미수료");

    private final String label;

    CourseStatus(String label) {

        this.label = label;
    }

    public String getLabel() {

        return label;
    }

    public static boolean isSupported(String label) {

        for (CourseStatus status : values()) {

            if (status.label.equals(label)) return true;
        }

        return false;
    }
}
