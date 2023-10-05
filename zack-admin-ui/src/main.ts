import {createApp} from 'vue'
import App from '@/App.vue'
import router from "@/router";
import '@/style.css'
import pinia from "@/store";
import 'element-plus/theme-chalk/src/index.scss'
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import '@/permission'
import {setupAuth} from "@/directives";
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import '@/assets/icon/iconfont.css'



const app = createApp(App)
app.use(router)
app.use(pinia)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
app.use(ElementPlus, {
    locale: zhCn,
})
setupAuth(app)
app.mount('#app')