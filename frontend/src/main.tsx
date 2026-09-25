/*
 * EduHistory 애플리케이션 진입점
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 기본 스타일(index.css) 임포트 제거
 */
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
