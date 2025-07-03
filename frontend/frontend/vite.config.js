import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      // redirige cualquier /api/** a http://localhost:8081/api/**
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        // si tu backend no usa prefijo /api, podrías descomentar el rewrite:
        // rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
