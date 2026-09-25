/*
 * EduHistory 목록 조회 및 다운로드 요청을 처리하는 API 함수
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
import type { EduHistoryListResponse, SearchCondition } from "./types"

const BASE_URL = '/api/edu-histories';

/** 검색 조건을 쿼리 문자열로 변환한다. 빈 값은 제외한다. */
export function toQueryString(condition: SearchCondition): string {

    const params = new URLSearchParams();
    
    if (condition.searchType)
        params.set('searchType', condition.searchType);
    if (condition.keyword.trim())
        params.set('keyword', condition.keyword.trim());
    if (condition.startDate)
        params.set('startDate', condition.startDate);
    if (condition.endDate)
        params.set('endDate', condition.endDate);
    if (condition.status)
        params.set('status', condition.status);

    return params.toString();
}

/** 목록 조회 */
export async function fetchEduHistories(
    condition: SearchCondition,
): Promise<EduHistoryListResponse> {

    const query = toQueryString(condition);
    const response = await fetch(query ? `${BASE_URL}?${query}` : BASE_URL);

    if (!response.ok) {

        const error = await response.json().catch(() => ({}));
        throw new Error(error.message ?? '조회 중 오류가 발생했습니다.');
    }

    return response.json();
}

/** 다운로드 URL 생성 */
export function buildDownloadUrl(condition: SearchCondition): string {

    const query = toQueryString(condition);
    return query ? `${BASE_URL}/download?${query}` : `${BASE_URL}/download`;
}