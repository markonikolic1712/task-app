import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 10000,
})

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API error:', error)
    return Promise.reject(error)
  },
)

async function GET(path) {
  try {
    const response = await axiosInstance.get(path, {})
    //console.log('axiosconfig.js - GET response: ', response.data)
    if (response.data) return response.data
  } catch (error) {
    throw new Error(error.message)
  }
}

const api = { GET }
export default api
