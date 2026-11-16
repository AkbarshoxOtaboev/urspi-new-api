import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(), tailwindcss()],
  // Empty base so assets work when served from Spring Boot at /
  base: '/',
  server: {
    proxy: {
      '/api': {
        // Local backend (docker/maven). Change if API runs elsewhere.
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
    },
  },
})
