import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
// https://vitejs.dev/config/
export default defineConfig({
plugins: [vue()],
  server: {
    host: '0.0.0.0', //解决控制台 ：Network: use --host to expose
    port: 8080, //配置端口号
    hmr: true, //开启热更新
    open: true, //启动在浏览器打开
    proxy: {
      '/api': {
        target: 'http://localhost:8079/', //接口地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  },
  resolve: {
    alias: [
      {
        find: '@',
        replacement: resolve(__dirname, 'src')
      }
    ]
  }
})