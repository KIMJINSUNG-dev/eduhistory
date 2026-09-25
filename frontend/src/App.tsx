/*
 * EduHistory 검색 조건 및 조회 결과 상태를 관리하는 루트 컴포넌트
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
import { useCallback, useEffect, useState } from 'react'
import SearchForm from './SearchForm'
import EduHistoryTable from './EduHistoryTable'
import { buildDownloadUrl, fetchEduHistories } from './api'
import { EMPTY_CONDITION, type EduHistory, type SearchCondition } from './types'
import './App.css'

function App() {
  
  /** 입력 중인 조건 (사용자가 타이핑하는 값) */
  const [condition, setCondition] = useState<SearchCondition>(EMPTY_CONDITION);

  /** 마지막으로 검색을 실행한 조건 (다운로드 기준) */
  const [appliedCondition, setAppliedCondition] = useState<SearchCondition>(EMPTY_CONDITION);

  const [list, setList] = useState<EduHistory[]>([]);
  const [totalCount, setTotalCount] = useState(0);
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const search = useCallback(async (target: SearchCondition) => {

    setLoading(true);
    setErrorMessage('');
    try {

      const data = await fetchEduHistories(target);
      setList(data.list);
      setTotalCount(data.totalCount);
      setAppliedCondition(target);
    } catch (e) {

      setErrorMessage(e instanceof Error ? e.message : '조회 중 오류가 발생했습니다.');
      setList([]);
      setTotalCount(0);
    } finally {

      setLoading(false);
    }
  }, []);

  /** 최초 진입 시 전체 조회 */
  useEffect(() => {

    search(EMPTY_CONDITION);
  }, [search]);

  const handleDownload = () => {

    window.location.href = buildDownloadUrl(appliedCondition);
  }

  return (

    <div className="container">
      <h1>교육 수강내역</h1>

      <SearchForm
        condition={condition}
        onChange={setCondition}
        onSearch={() => search(condition)}
      />

      {errorMessage && <p className="error">{errorMessage}</p>}

      <div className="list-header">
        <span className="total">총 {totalCount}건</span>
        <button
          type="button"
          onClick={handleDownload}
          disabled={loading}
        >
          엑셀 다운로드
        </button>
      </div>

      {loading ? <p className="loading">조회 중...</p> : <EduHistoryTable list={list} />}
    </div>
  );
}

export default App;