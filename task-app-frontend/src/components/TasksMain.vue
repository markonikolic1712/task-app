<template>
  <div class="flex-1 bg-app-gray-one mx-auto p-5">
    <h1 class="font-bold py-2 text-[clamp(28px,1.8vw,38px)]">Your Tasks</h1>

    <div v-if="tasksStore.loading" class="flex space-x-3">
      <ion-icon name="reload-outline" class="text-3xl animate-spin text-gray-300"></ion-icon>
      <p>Loading...</p>
    </div>
    <div v-else-if="tasksStore.taskList.length > 0">
      <TaskItem v-for="task in tasksStore.taskList" :key="task.id" :taskId="task.id" />
      
    </div>
    <div v-else>
      <h1 class="font-bold py-2 text-[clamp(28px,1.8vw,38px)]">Tasks not found</h1>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed, reactive, onUpdated, nextTick } from 'vue'
import { useTaskAppStore } from '@/stores/tasks'
import TaskItem from './TaskItem.vue'

const tasksStore = useTaskAppStore()

onMounted(() => {
  console.log('mounted')
  tasksStore.fetchTasks()
})
</script>

<style scoped></style>
