<template>
  <div class="flex flex-col bg-app-gray-three rounded-lg p-3">
    <div class="flex justify-between items-center">
      <div class="flex w-8 h-8 rounded-full items-center justify-center" :class="bgColor">
        <ion-icon :name="iconName" class="text-white"></ion-icon>
      </div>
      <div class="text-xl font-bold">{{ numberOfItems }}</div>
    </div>
    <div class="pt-1 text-[clamp(14px,1.45vw,18px)]">{{ itemsDescr }}</div>
  </div>
</template>

<script setup>
const { bgColor, numberOfItems, itemsDescr, iconName } = defineProps({
  bgColor: {
    type: String,
    default: 'bg-app-blue',
  },
  numberOfItems: {
    type: Number,
    default: 0,
  },
  itemsDescr: {
    type: String,
    default: 'Today',
  },
  iconName: {
    type: String,
    default: 'calendar-outline',
  },
})
</script>

<style scoped></style>
