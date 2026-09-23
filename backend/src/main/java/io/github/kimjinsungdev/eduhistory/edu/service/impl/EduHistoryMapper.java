/*
 * EduHistory DB 쿼리를 MyBatis로 매핑하는 Mapper 인터페이스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service.impl;

import io.github.kimjinsungdev.eduhistory.edu.service.EduHistorySearchVO;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EduHistoryMapper {

    int countAll();

    void insertAll(@Param("list")List<EduHistoryVO> list);

    List<EduHistoryVO> selectEduHistoryList(EduHistorySearchVO searchVO);
}
