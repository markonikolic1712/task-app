<template>
  <main>
    <div class="min-h-screen flex text-white font-normal">
      <Sidebar />
      <TasksMain />
    </div>
  </main>
</template>

<script setup>
import Sidebar from './components/Sidebar.vue'
import TasksMain from './components/TasksMain.vue'
</script>

<style scoped></style>
