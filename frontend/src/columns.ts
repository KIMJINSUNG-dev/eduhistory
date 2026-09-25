/*
 * EduHistory 목록 테이블의 컬럼 정의
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
import type { EduHistory } from './types'

export interface ColumnDef {

    /** 데이터 필드명 */
    key   : keyof EduHistory
    /** 화면에 표시할 헤더명 */
    label : string
    /** 본문 정렬 (기본 center) */
    align?: 'left' | 'center' | 'right'
}

/** 교육 수강내역 목록 컬럼 정의 */
export const EDU_HISTORY_COLUMNS: ColumnDef[] = [

    { key: 'traineeName',     label: '교육자' },
    { key: 'eduStartDate',    label: '교육시작일' },
    { key: 'eduEndDate',      label: '교육종료일' },
    { key: 'courseName',      label: '과정명', align: 'left' },
    { key: 'institutionName', label: '교육기관' },
    { key: 'courseStatus',    label: '수강상태' },
]