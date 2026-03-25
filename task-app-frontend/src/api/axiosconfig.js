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
    if (response.data !== undefined) return response.data
  } catch (error) {
    throw new Error(error.message)
  }
}

async function DELETE(path) {
  try {
    const response = await axiosInstance.delete(path, {})
    if (response.status === 204) return response.status
  } catch (error) {
    throw new Error(error.message)
  }
}

async function PUT(path, data) {
  try {
    const response = await axiosInstance.put(path, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    console.log('axiosconfig.js - PUT() response.status: ' + JSON.stringify(response.status))
    if (response.status === 200) return response.data
  } catch (error) {
    throw new Error(error.message)
  }
}

async function POST(path, data) {
  try {
    const response = await axiosInstance.post(path, data)
    console.log('axiosconfig.js - POST(): ' + JSON.stringify(response.data))
    if (response.status === 201) return response.data
  } catch (error) {
    throw new Error(error.message)
  }
}

const api = { GET, DELETE, PUT, POST }
export default api
