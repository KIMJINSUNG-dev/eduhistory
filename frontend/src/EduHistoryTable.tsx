/*
 * EduHistory 목록 테이블 컴포넌트
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 파일 생성 및 작성
 */
import { EDU_HISTORY_COLUMNS } from "./columns"
import type { EduHistory } from "./types"

interface Props {

    list: EduHistory[]
}

function EduHistoryTable({ list }: Props) {

    if (list.length === 0) {

        return <p className="empty">검색 결과가 없습니다.</p>
    }

    return (

        <div className="table-wrapper">
            <table>
                <thead>
                    <tr>
                        {EDU_HISTORY_COLUMNS.map((column) => (

                            <th key={column.key}>{column.label}</th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {list.map((item) => (

                        <tr key={item.id}>
                            {EDU_HISTORY_COLUMNS.map((column) => (

                                <td key={column.key} style={{ textAlign: column.align ?? 'center' }}>
                                    {item[column.key]}
                                </td>
                            ))}
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default EduHistoryTable;