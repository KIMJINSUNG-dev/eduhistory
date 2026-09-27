/*
 * EduHistory 검색 조건 검증 로직 테스트
 * 작성자: 김진성
 * 작성이력
 * -2026-09-27: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service.impl;

import io.github.kimjinsungdev.eduhistory.common.InvalidSearchConditionException;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistorySearchVO;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EduHistoryServiceImplTest {

    /** DB 접근 없이 검증 로직만 확인하기 위한 대역(stub) */
    static class StubMapper implements EduHistoryMapper {

        /** 서비스가 Mapper에 전달한 검색 조건 */
        EduHistorySearchVO capturedSearchVO;

        @Override
        public int countAll() {

            return 0;
        }

        @Override
        public void insertAll(List<EduHistoryVO> list) {

            // 테스트에서 사용하지 않음
        }

        @Override
        public List<EduHistoryVO> selectEduHistoryList(EduHistorySearchVO searchVO) {

            this.capturedSearchVO = searchVO;
            return Collections.emptyList();
        }
    }

    private final StubMapper stubMapper = new StubMapper();
    private final EduHistoryServiceImpl service = new EduHistoryServiceImpl(stubMapper);

    private EduHistorySearchVO searchVO(String startDate, String endDate) {

        EduHistorySearchVO searchVO = new EduHistorySearchVO();
        searchVO.setStartDate(startDate);
        searchVO.setEndDate(endDate);
        return searchVO;
    }

    @Test
    @DisplayName("교육 시작일/교육 종료일 중 한 쪽만 입력하면 예외 발생")
    void throwsWhenOnlyOneDateGiven() {

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(searchVO("2024-06-10", null)));

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(searchVO(null, "2024-06-20")));
    }

    @Test
    @DisplayName("존재하지 않는 날짜를 입력하면 예외 발생")
    void throwsWhenDateIsInvalid() {

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(searchVO("2024-02-30", "2024-03-01")));
    }

    @Test
    @DisplayName("교육 시작일이 교육 종료일보다 늦으면 예외 발생")
    void throwsWhenDateRangeIsReversed() {

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(searchVO("2024-06-20", "2024-06-10")));
    }

    @Test
    @DisplayName("교육 시작일/교육 종료일을 모두 입력하지 않으면 기간 조건을 적용하지 않음")
    void doesNotApplyPeriodWhenBothDatesAreEmpty() {

        assertDoesNotThrow(() -> service.selectEduHistoryList(searchVO(null, null)));

        assertNull(stubMapper.capturedSearchVO.getFromDate());
        assertNull(stubMapper.capturedSearchVO.getToDate());
    }

    @Test
    @DisplayName("유효한 교육 시작일/교육 종료일을 입력하면 조회 조건으로 변환")
    void convertsValidDatesToSearchCondition() {

        service.selectEduHistoryList(searchVO("2024-06-10", "2024-06-20"));

        assertEquals(LocalDate.of(2024, 6, 10), stubMapper.capturedSearchVO.getFromDate());
        assertEquals(LocalDate.of(2024, 6, 20), stubMapper.capturedSearchVO.getToDate());
    }

    @Test
    @DisplayName("지원하지 않는 검색 구분이나 수강상태 입력 시 예외 발생")
    void throwsWhenCodeValueIsNotSupported() {

        EduHistorySearchVO invalidSearchType = new EduHistorySearchVO();
        invalidSearchType.setSearchType("unknownSearchType");

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(invalidSearchType));

        EduHistorySearchVO invalidStatus = new EduHistorySearchVO();
        invalidStatus.setStatus("unknownStatus");

        assertThrows(InvalidSearchConditionException.class,
                () -> service.selectEduHistoryList(invalidStatus));
    }

    @Test
    @DisplayName("검색어의 앞뒤 공백을 제거하고 빈 값은 조건에서 제외")
    void trimsKeywordAndExcludesBlank() {

        EduHistorySearchVO withSpaces = new EduHistorySearchVO();
        withSpaces.setKeyword("  리더십  ");
        service.selectEduHistoryList(withSpaces);
        assertEquals("리더십", stubMapper.capturedSearchVO.getKeyword());

        EduHistorySearchVO blank = new EduHistorySearchVO();
        blank.setKeyword("    ");
        service.selectEduHistoryList(blank);
        assertNull(stubMapper.capturedSearchVO.getKeyword());
    }
}
