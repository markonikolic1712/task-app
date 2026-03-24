import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getAllTasks, deleteTask, updateTaskById, createTask } from '@/api/taskAppService'
import { useToast } from 'vue-toastification'
import { ToastMessages } from '@/constants/toastMessages'

const toast = useToast()

export const useTaskAppStore = defineStore('tasks', () => {
  //////////////////////////////////////////////////////////
  ////////////////// properties - pocetak //////////////////
  //////////////////////////////////////////////////////////
  // definise se property u state-u koji ce sadrzati niz Recepie objekata
  const taskList = ref([])
  // const taskList = ref([
  //   {
  //     id: 1,
  //     title: 'Task 2',
  //     description: 'Task 2 description',
  //     priority: 'LOW',
  //     dueDate: '03/08/2026',
  //     status: 'OPEN',
  //   },
  //   {
  //     id: 2,
  //     title: 'Task 4',
  //     description: 'Task 4 description',
  //     priority: 'MEDIUM',
  //     dueDate: '2/28/2026',
  //     status: 'COMPLETED',
  //   },
  //   {
  //     id: 3,
  //     title: 'Task 5',
  //     description: 'Task 5 description',
  //     priority: 'MEDIUM',
  //     dueDate: '3/08/2027',
  //     status: 'COMPLETED',
  //   },
  //   {
  //     id: 4,
  //     title: 'Task 1',
  //     description: 'Task 1 description',
  //     priority: 'HIGH',
  //     dueDate: '1/30/2027',
  //     status: 'OPEN',
  //   },
  //   {
  //     id: 5,
  //     title: 'Task 1',
  //     description: 'Task 1 description',
  //     priority: 'HIGH',
  //     dueDate: '2/20/2026',
  //     status: 'OPEN',
  //   },
  // ])
  const favoritieIds = ref([])
  const loading = ref(false)
  const username = ref('pera')
  ///////////////////////////////////////////////////////
  ////////////////// properties - kraj //////////////////
  ///////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////
  ////////////////// actions - pocetak //////////////////
  ///////////////////////////////////////////////////////
  const fetchTasks = async () => {
    loading.value = true
    try {
      const data = await getAllTasks()
      taskList.value = data
    } finally {
      loading.value = false
    }
    taskList.value.forEach((t) => {
      t.username = username.value
      //console.log(JSON.stringify(t))
    })
  }

  const toggleTaskStatus = async (id) => {
    let taskToUpdate

    taskList.value.forEach((task) => {
      if (task.id === id) {
        task.status = task.status === 'COMPLETED' ? 'OPEN' : 'COMPLETED'
        taskToUpdate = { ...task }
      }
    })
    await updateTask(taskToUpdate)
  }

  const deleteTaskById = async (taskId) => {
    //const taskListBck = taskList.value
    const taskListBck = JSON.parse(JSON.stringify(taskList.value))
    // ili const taskListBck = structuredClone(taskList.value)
    try {
      const task = taskList.value.find((t) => t.id === taskId)
      taskList.value.splice(taskList.value.indexOf(task), 1)
      const response = await deleteTask(taskId)
      // Axios već baca grešku ako status nije 2xx tako da provera (response != 204) ne mora da se radi
      // if (response != 204) {
      //   console.log('Doslo je do problema prilikom brisanja taska')
      //   taskList.value = taskListBck
      // }
      await fetchTasks()
      toast.success(ToastMessages.TASK_DELETED)
    } catch (error) {
      console.log('Doslo je do problema prilikom brisanja taska. ' + error)
      taskList.value = [...taskListBck]
      toast.error(ToastMessages.TASK_DELETE_ERROR)
      throw error
    }
  }

  const saveTask = async (task) => {
    //const task = { ...t, username: username.value }
    task.username = username.value

    const taskListBck = JSON.parse(JSON.stringify(taskList.value))
    try {
      const response = await createTask(task)
      await fetchTasks()
      toast.success(ToastMessages.TASK_SAVED)
    } catch (error) {
      console.log('Doslo je do problema prilikom kreiranja taska. ' + error)
      taskList.value = [...taskListBck]
      toast.error(ToastMessages.TASK_SAVE_ERROR)
      throw error
    }
  }

  const updateTask = async (task) => {
    task.username = username.value
    const taskListBck = JSON.parse(JSON.stringify(taskList.value))
    try {
      const response = await updateTaskById(task.id, task)
      await fetchTasks()
      toast.success(ToastMessages.TASK_UPDATED)
    } catch (error) {
      console.log('Doslo je do problema prilikom izmene taska. ' + error)
      taskList.value = [...taskListBck]
      toast.error(ToastMessages.TASK_UPDATE_ERROR)
      throw error
    }
  }
  ///////////////////////////////////////////////////////
  /////////////////// actions - kraj ////////////////////
  ///////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////
  ////////////////// getters - pocetak //////////////////
  ///////////////////////////////////////////////////////
  // uzimanje recepie objekta po id-u
  const getTaskById = (id) => {
    return taskList.value.find((r) => r.id === id)
  }

  const getTasksByStatus = (status) => {
    const r = taskList.value.filter((r) => r.status == status)
    if (r == undefined) return []
    return r
  }

  const getTasksByPriority = (priority) => {
    const r = taskList.value.filter((r) =>
      r.priority.toLowerCase().includes(priority.toLowerCase()),
    )
    if (r == undefined) return []
    return r
  }

  const countToday = computed(() => {
    const today = new Date().toDateString()
    return taskList.value.filter((task) => {
      if (!task.dueDate) return false
      return new Date(task.dueDate).toDateString() === today
    }).length
  })

  const countScheduled = computed(() => taskList.value.length)
  const countAll = computed(() => taskList.value.length)
  const countCompleted = computed(() => getTasksByStatus('COMPLETED').length)
  ////////////////////////////////////////////////////
  ////////////////// getters - kraj //////////////////
  ////////////////////////////////////////////////////

  return {
    favoritieIds,
    loading,
    taskList,
    getTaskById,
    getTasksByPriority,
    getTasksByStatus,
    countToday,
    countScheduled,
    countAll,
    countCompleted,
    fetchTasks,
    toggleTaskStatus,
    deleteTaskById,
    saveTask,
    updateTask,
  }
})
