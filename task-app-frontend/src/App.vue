<template>
  <main>
    <div class="h-screen conatiner flex text-white font-normal">
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
