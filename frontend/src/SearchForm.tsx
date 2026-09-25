/*
 * EduHistory 검색 조건 입력 폼 컴포넌트
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
import type { SearchCondition } from "./types"

interface Props {

    condition: SearchCondition
    onChange : (condition: SearchCondition) => void
    onSearch : () => void
}

function SearchForm({ condition, onChange, onSearch }: Props) {

    const update = (key: keyof SearchCondition, value: string) => {

        onChange({ ...condition, [key]: value });
    }

    return (

        <div className="search-box">
            <div className="search-row">
                <label className="search-label">검색어</label>
                <select
                    value={condition.searchType}
                    onChange={(e) => update('searchType', e.target.value)}
                >
                    <option value="all">전체</option>
                    <option value="course">과정명</option>
                    <option value="institution">교육기관</option>
                </select>
                <input
                    type="text"
                    className="keyword"
                    placeholder="검색어를 입력하세요"
                    value={condition.keyword}
                    onChange={(e) => update('keyword', e.target.value)}
                    onKeyDown={(e) => {

                        if (e.key === 'Enter') onSearch();
                    }}
                />
            </div>
            
            <div className="search-row">
                <label className="search-label">교육일</label>
                <input
                    type="date"
                    value={condition.startDate}
                    onChange={(e) => update('startDate', e.target.value)}
                />
                <span>~</span>
                <input
                    type="date"
                    value={condition.endDate}
                    onChange={(e) => update('endDate', e.target.value)}
                />
            </div>

            <div className="search-row">
                <label className="search-label">수강상태</label>
                <select
                    value={condition.status}
                    onChange={(e) => update('status', e.target.value)}
                >
                    <option value="">전체</option>
                    <option value="수료">수료</option>
                    <option value="수강">수강</option>
                    <option value="미수료">미수료</option>
                </select>
                <button
                    type="button"
                    className="search-button"
                    onClick={onSearch}
                >
                    검색
                </button>
            </div>
        </div>
    )
}

export default SearchForm;