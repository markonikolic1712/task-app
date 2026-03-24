import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
// vue-toastification
import Toast, { POSITION } from 'vue-toastification'
import 'vue-toastification/dist/index.css'

const app = createApp(App)

app.use(createPinia())

// registracija plugin-a
// sa POSITION.BOTTOM_RIGHT see setuje da notifikacija bude dole desno
app.use(Toast, {
  position: POSITION.BOTTOM_RIGHT, // toastovi dole desno. Ovo dole desno je u odnosu na <body> tj. celu aplikaciju
  timeout: 3000, // trajanje u ms
  closeOnClick: true,
  pauseOnHover: true,
  newestOnTop: true, // novije notifikacije iznad starijih
})

app.mount('#app')
