CREATE TABLE IF NOT EXISTS edu_history (
    id                INT          NOT NULL AUTO_INCREMENT COMMENT '원본 순서 유지용 식별자',
    trainee_name      VARCHAR(50)  NOT NULL COMMENT '교육자',
    edu_start_date    DATE         NOT NULL COMMENT '교육시작일',
    edu_end_date      DATE         NOT NULL COMMENT '교육종료일',
    course_name       VARCHAR(200) NOT NULL COMMENT '과정명',
    institution_name  VARCHAR(100) NOT NULL COMMENT '교육기관',
    course_status     VARCHAR(10)  NOT NULL COMMENT '수강상태',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='교육 수강내역';