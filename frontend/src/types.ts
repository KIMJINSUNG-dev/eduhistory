/*
 * EduHistory 응답 데이터 및 검색 조건 타입 정의
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
export interface EduHistory {

    id:              number
    traineeName:     string
    eduStartDate:    string
    eduEndDate:      string
    courseName:      string
    institutionName: string
    courseStatus:    string
}

export interface EduHistoryListResponse {

    totalCount: number
    list      : EduHistory[]
}

/** 검색 조건 (화면 입력값) */
export interface SearchCondition {

    searchType: string
    keyword   : string
    startDate : string
    endDate   : string
    status    : string
}

export const EMPTY_CONDITION: SearchCondition = {

    searchType: 'all',
    keyword   : '',
    startDate : '',
    endDate   : '',
    status    : '',
}