<template>
  <div class="flex flex-col w-1/5 min-w-[200px] max-w-[400px] bg-app-gray-two p-2">
    <!-- <h1 class="font-bold py-2 text-2xl md:text-3xl">Task App</h1> -->
    <h1 class="font-bold py-2 text-[clamp(20px,1.5vw,26px)]">Task App</h1>
    <div class="grid grid-cols-2 space-x-2 mb-2">
      <TaskSum
        :bgColor="'bg-app-blue'"
        :numberOfItems="tasks.countToday"
        :itemsDescr="'Today'"
        :iconName="'calendar-clear-outline'"
      />
      <TaskSum
        :bgColor="'bg-app-red'"
        :numberOfItems="tasks.countScheduled"
        :itemsDescr="'Scheduled'"
        :iconName="'calendar-outline'"
      />
    </div>
    <div class="grid grid-cols-2 space-x-2">
      <TaskSum
        :bgColor="'bg-app-gray'"
        :numberOfItems="tasks.countAll"
        :itemsDescr="'All'"
        :iconName="'file-tray-full-outline'"
      />
      <TaskSum
        :bgColor="'bg-app-green'"
        :numberOfItems="tasks.countCompleted"
        :itemsDescr="'Completed'"
        :iconName="'checkmark-outline'"
      />
    </div>
  </div>
</template>

<script setup>
import TaskSum from './TasksSum.vue'
import { useTaskAppStore } from '@/stores/tasks'

const tasks = useTaskAppStore()
</script>

<style scoped></style>
