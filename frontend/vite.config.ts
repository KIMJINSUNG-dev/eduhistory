/*
 * Vite 개발 서버 설정 파일
 * 작성자: 김진성
 * 작성이력
 * -2026-09-25: 개발 서버 API 요청 프록시 설정 추가
 */
import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})
