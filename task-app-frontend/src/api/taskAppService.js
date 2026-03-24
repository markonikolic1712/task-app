import api from './axiosconfig'

async function getAllTasks() {
  const response = await api.GET('/api/v1/tasks')
  return response
}

async function deleteTask(id) {
  const response = await api.DELETE(`/api/v1/tasks/${id}`)
  return response
}

async function updateTaskById(id, task) {
  const response = await api.PUT(`/api/v1/tasks/${id}`, task)
  return response
}

async function createTask(task) {
  const response = await api.POST(`/api/v1/tasks`, task)
  return response
}

export { getAllTasks, deleteTask, updateTaskById, createTask }
