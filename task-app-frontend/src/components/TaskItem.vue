<template>
  <div class="max-w-[800px]">
    <div class="flex">
      <div class="w-16 flex justify-center">
        <ion-icon
          v-if="task?.status === 'COMPLETED'"
          name="checkbox"
          class="cursor-pointer self-center text-2xl text-app-green"
          @click="toggleStatus(taskId)"
        ></ion-icon>
        <ion-icon
          v-else
          name="square-outline"
          class="cursor-pointer self-center text-2xl text-white"
          @click="toggleStatus(taskId)"
        ></ion-icon>
      </div>
      <div class="flex-1">
        <div class="flex flex-col text-[clamp(16px,1.0vw,20px)]">
          <h1 class="font-medium text-[clamp(18px,1.2vw,24px)]">{{ task.title }}</h1>
          <p class="text-app-gray">{{ task.description }}</p>
          <div class="flex text-app-gray items-center space-x-2">
            <!-- <ion-icon name="flag-outline" :class="getFlagColor(task.priority)"></ion-icon> -->
            <ion-icon
              name="flag-outline"
              :style="{ color: getFlagColor(task.priority) }"
            ></ion-icon>
            <span class="pr-2">{{ task.priority }}</span>
            <ion-icon name="calendar-outline"></ion-icon>
            <span>{{ getFormattedDueDate(task.dueDate) }}</span>
          </div>
        </div>
      </div>
      <div class="flex w-1/5 min-w-[50px] max-w-[250px] space-x-1 justify-end pr-5">
        <ion-icon
          name="create-outline"
          class="cursor-pointer self-center text-2xl"
          @click="editTask(task)"
        ></ion-icon>
        <ion-icon
          name="trash-outline"
          class="cursor-pointer self-center text-2xl"
          @click="deleteTask(task)"
        ></ion-icon>
      </div>
    </div>
    <hr class="border-app-gray-three my-1" />
    <TaskModal v-model="isModalOpen" :task="selectedTask" @submit="handleSubmit" />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useTaskAppStore } from '@/stores/tasks'
import { useToast } from 'vue-toastification'
import TaskModal from './TaskModal.vue'

const tasksStore = useTaskAppStore()

const isModalOpen = ref(false)
const selectedTask = ref(null)
const isEditMode = ref(false)
//const priorityColor = ref('')

const { taskId, priorityColor } = defineProps({
  taskId: { type: String, required: true },
  priorityColor: { type: String },
})

// direktan getter iz store-a
const task = computed(() => tasksStore.getTaskById(taskId))

// lokalni reactive props za ion-icon
//const isCompleted = computed(() => task.value?.status === 'COMPLETED')
//const iconName = computed(() => (isCompleted.value ? 'checkbox' : 'square-outline'))

const toggleStatus = (id) => {
  tasksStore.toggleTaskStatus(id)
}

const getFormattedDueDate = (dueDate) => {
  return new Date(dueDate).toLocaleDateString('sr-RS', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  })
}

const getFlagColor = (priority) => {
  // if (priority === 'LOW') return 'text-app-blue'
  // if (priority === 'MEDIUM') return 'text-app-green'
  // if (priority === 'HIGH') return 'text-app-red'
  if (priority === 'LOW') return '#3B82F4'
  if (priority === 'MEDIUM') return '#22C55E'
  if (priority === 'HIGH') return '#EF4444'
}

const editTask = (task) => {
  isEditMode.value = true
  openModal(task)
}

const deleteTask = (task) => {
  tasksStore.deleteTaskById(task.id)
}

const openModal = (task = null) => {
  selectedTask.value = task
  isModalOpen.value = true
}

const handleSubmit = async (data) => {
  await tasksStore.updateTask(data)
}

// const handleSubmit = async (data) => {
//   if (data.id === null) {
//     console.log('TaskItem.vue - data.id === null: ' + JSON.stringify(data))
//     await tasksStore.saveTask(data)
//   } else {
//     console.log('TaskItem.vue - data.id !== null: ' + JSON.stringify(data))
//     await tasksStore.updateTask(data)
//   }
// }
</script>
