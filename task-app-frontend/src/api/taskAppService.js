import api from './axiosconfig'

async function getAllTasks() {
  const response = await api.GET('/api/v1/tasks')
  return response
}

export { getAllTasks }
