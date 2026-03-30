<template>
  <main>
    <div class="bg-app-gray-one min-h-screen flex flex-col md:flex-row text-white font-normal">
      <Sidebar class="w-full md:w-auto" />
      <TasksMain class="w-full md:w-auto" />
    </div>
  </main>
</template>

<script setup>
import Sidebar from './components/Sidebar.vue'
import TasksMain from './components/TasksMain.vue'
</script>

<style scoped></style>
