import {createPinia, Pinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia: Pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
export default pinia