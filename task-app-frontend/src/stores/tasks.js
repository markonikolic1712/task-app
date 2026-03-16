import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getAllTasks } from '@/api/taskAppService'

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
  }

  const toggleTaskStatus = (id) => {
    taskList.value.forEach((task) => {
      if (task.id === id) {
        task.status = task.status === 'COMPLETED' ? 'OPEN' : 'COMPLETED'
      }
    })
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
  }
})
