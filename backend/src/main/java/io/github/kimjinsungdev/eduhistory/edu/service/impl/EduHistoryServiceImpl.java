/*
 * EduHistoryService 인터페이스를 구현하여 목록을 반환할 수 있도록 하는 서비스 클래스
 * 작성자: 김진성
 * 작성이력
 * -2026-09-23: 파일 생성 및 작성
 */
package io.github.kimjinsungdev.eduhistory.edu.service.impl;

import io.github.kimjinsungdev.eduhistory.common.InvalidSearchConditionException;
import io.github.kimjinsungdev.eduhistory.edu.code.CourseStatus;
import io.github.kimjinsungdev.eduhistory.edu.code.SearchType;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryListResponse;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistorySearchVO;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryService;
import io.github.kimjinsungdev.eduhistory.edu.service.EduHistoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EduHistoryServiceImpl implements EduHistoryService {

    private final EduHistoryMapper eduHistoryMapper;

    @Override
    @Transactional(readOnly = true)
    public EduHistoryListResponse selectEduHistoryList(EduHistorySearchVO searchVO) {

        validateAndNormalize(searchVO);
        List<EduHistoryVO> list = eduHistoryMapper.selectEduHistoryList(searchVO);
        return new EduHistoryListResponse(list);
    }

    /** 검색 조건을 검증하고 SQL에서 쓸 형태로 정리한다. */
    private void validateAndNormalize(EduHistorySearchVO searchVO) {

        // 검색 구분: 미입력 시 전체
        if (isEmpty(searchVO.getSearchType())) {

            searchVO.setSearchType(SearchType.ALL.getCode());
        } else if (!SearchType.isSupported(searchVO.getSearchType())) {

            throw new InvalidSearchConditionException("검색 구분 값이 올바르지 않습니다.");
        }

        // 검색어: 앞뒤 공백 제거, 빈 값이면 조건 미적용
        if (searchVO.getKeyword() != null) {

            String keyword = searchVO.getKeyword().trim();
            searchVO.setKeyword(keyword.isEmpty() ? null : keyword);
        }

        // 수강상태
        if (isEmpty(searchVO.getStatus())) {

            searchVO.setStatus(null);
        } else if (!CourseStatus.isSupported(searchVO.getStatus())) {

            throw new InvalidSearchConditionException("수강상태 값이 올바르지 않습니다.");
        }

        // 교육일
        boolean hasStart = !isEmpty(searchVO.getStartDate());
        boolean hasEnd   = !isEmpty(searchVO.getEndDate());

        if (!hasStart && !hasEnd) {

            return; // 두 날짜 모두 미입력: 기간 조건 미적용
        }

        if (hasStart != hasEnd) {

            throw new InvalidSearchConditionException("교육 시작일과 교육 종료일을 모두 입력해 주세요.");
        }

        LocalDate from = parseDate(searchVO.getStartDate());
        LocalDate to   = parseDate(searchVO.getEndDate());

        if (from.isAfter(to)) {

            throw new InvalidSearchConditionException("교육 시작일은 교육 종료일보다 늦을 수 없습니다.");
        }

        searchVO.setFromDate(from);
        searchVO.setToDate(to);
    }

    private LocalDate parseDate(String value) {

        try {

            return LocalDate.parse(value.trim());
        } catch (DateTimeParseException e) {

            throw new InvalidSearchConditionException("날짜 형식이 올바르지 않습니다. (YYYY-MM-DD)");
        }
    }

    private boolean isEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }
}
