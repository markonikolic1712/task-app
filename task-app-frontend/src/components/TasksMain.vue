<template>
  <div class="flex-1 bg-app-gray-one mx-auto p-5 flex flex-col">
    <h1 class="font-bold py-2 text-[clamp(28px,1.8vw,38px)]">Your Tasks</h1>

    <div v-if="showLoader" class="flex space-x-3 animate-fade-in">
      <ion-icon name="reload-outline" class="text-3xl animate-spin text-gray-300"></ion-icon>
      <p>Loading...</p>
    </div>

    <div
      v-else-if="tasksStore.taskList.length > 0"
      class="max-h-[500px] overflow-y-auto scrollbar-thin scrollbar-thumb-rounded-full scrollbar-thumb-gray-600 scrollbar-track-transparent hover:scrollbar-thumb-gray-500"
    >
      <TaskItem v-for="task in tasksStore.taskList" :key="task.id" :taskId="task.id" />
    </div>

    <div v-else>
      <h1 v-if="isEmpty" class="font-bold py-2 text-[clamp(28px,1.8vw,38px)]">Tasks not found</h1>
    </div>
    <div
      class="flex sm:w-[180px] w-full p-2 mt-2 text-black font-bold bg-white justify-center rounded-md cursor-pointer align-middle select-none"
      @click="openModal()"
    >
      <ion-icon name="add-circle-outline" class="self-center text-2xl text-black pr-2"></ion-icon>
      Create task
    </div>
    <TaskModal v-model="isModalOpen" :task="selectedTask" @submit="handleSubmit" />
  </div>
</template>

<script setup>
import { onMounted, ref, watch, computed } from 'vue'
import { useTaskAppStore } from '@/stores/tasks'
import TaskItem from './TaskItem.vue'
import TaskModal from './TaskModal.vue'

const tasksStore = useTaskAppStore()

const isModalOpen = ref(false)
const selectedTask = ref(null)

// Delay loader state
const showLoader = ref(false)
let loaderTimeout = null
// prati se loading iz store-a i odlaze se pojavljivanje Loading... i spiner ikone za 300ms
// kada god se radi fetch task liste sa backenda na tren se pojavi Loading... i nestane kada se lista fetchuje
watch(
  () => tasksStore.loading,
  (loading) => {
    if (loading) {
      // postavi timeout od 300ms pre nego sto prikazes spinner
      loaderTimeout = setTimeout(() => {
        showLoader.value = true
      }, 300)
    } else {
      // clear timeout i sakrij spinner
      clearTimeout(loaderTimeout)
      showLoader.value = false
    }
  },
)

// computed za empty state
const isEmpty = computed(() => !tasksStore.loading && tasksStore.taskList.length === 0)

const openModal = (task = null) => {
  selectedTask.value = task
  isModalOpen.value = true
}

const handleSubmit = async (data) => {
  console.log('TaskMain.vue: ' + JSON.stringify(data))
  await tasksStore.saveTask(data)
}

onMounted(() => {
  tasksStore.fetchTasks()
})
</script>

<style scoped></style>
