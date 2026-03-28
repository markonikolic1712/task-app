<template>
  <div v-if="modelValue" class="fixed inset-0 bg-black/80 flex items-center justify-center z-50">
    <div class="w-full max-w-md rounded-xl p-6 shadow-lg relative bg-app-gray-two">
      <!-- Close -->
      <button class="absolute top-3 right-3 text-gray-500 hover:text-black" @click="close">
        ✕
      </button>

      <!-- Title -->
      <h2 class="text-xl font-bold mb-4">
        {{ isEditMode ? 'Edit Task' : 'Create Task' }}
      </h2>

      <!-- Form -->
      <form @submit.prevent="handleSubmit" class="flex flex-col space-y-3">
        <input
          v-model="localForm.title"
          type="text"
          placeholder="Title"
          class="border p-2 rounded-md focus:outline-none focus:ring-1 focus:ring-white bg-app-gray-three"
          required
        />

        <textarea
          v-model="localForm.description"
          placeholder="Description"
          class="border p-2 rounded-md focus:outline-none focus:ring-1 focus:ring-white bg-app-gray-three"
        ></textarea>

        <div class="flex flex-1 text-xl">
          <h1 class="w-3/6">Due Date</h1>
          <h1 class="w-3/6">Priority</h1>
        </div>

        <div class="flex flex-1 text-xl space-x-1">
          <div class="w-3/6 text-black">
            <!-- <VueTailwindDatepicker
              :key="localForm.dueDate"
              v-model="localForm.dueDate"
              :formatter="formatter"
              as-single
              :use-range="false"
              :placeholder="dueDatePlaceholder"
              value-format="YYYY-MM-DD"
              input-classes="w-full bg-app-gray-three text-white border focus:ring-1 focus:ring-white rounded px-3 py-2"
            /> -->
            <VueTailwindDatepicker
              :key="localForm.dueDate"
              v-model="localForm.dueDate"
              :formatter="formatter"
              as-single
              :use-range="false"
              :placeholder="dueDatePlaceholder"
              value-format="YYYY-MM-DD"
              input-classes="w-full bg-app-gray-three text-white border focus:ring-1 focus:ring-white rounded px-3 py-2"
            />
          </div>
          <select
            v-model="localForm.priority"
            class="w-3/6 bg-app-gray-three text-white border rounded px-3 py-2 focus:ring-1 focus:ring-white"
          >
            <option value="LOW">Low</option>
            <option value="MEDIUM">Medium</option>
            <option value="HIGH">High</option>
          </select>
        </div>

        <div class="flex justify-end space-x-2 pt-2">
          <button
            type="button"
            class="px-4 py-2 rounded-md bg-app-gray-three hover:bg-gray-600 select-none"
            @click="close"
          >
            Cancel
          </button>

          <button
            type="submit"
            class="px-4 py-2 rounded-md bg-blue-700 text-white hover:bg-blue-500 select-none"
          >
            {{ isEditMode ? 'Update' : 'Create' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { watch, ref, computed, onMounted } from 'vue'
import VueTailwindDatepicker from 'vue-tailwind-datepicker'
import dayjs from 'dayjs'
import customParseFormat from 'dayjs/plugin/customParseFormat'

dayjs.extend(customParseFormat)

const dueDatePlaceholder = computed(() => {
  if (props.task) {
    return dayjs(props.task.dueDate).format('DD.MM.YYYY.')
  } else {
    return dayjs().format('DD.MM.YYYY.')
  }
})

// ovime se formatira placeholder - ako se ne koristi ovaj formater prikazace sate, minute i sekunde
const formatter = ref({
  date: 'DD.MM.YYYY.',
  month: 'MMM',
})

// props
const props = defineProps({
  modelValue: Boolean, // za v-model
  task: Object,
})

// emit
// sa emit('update:modelValue', false) se menja stanje v-model u parentu u false i modal se zatvara. U parentu je v-model="isModalOpen"
const emit = defineEmits(['update:modelValue', 'submit'])

// local state (da ne mutiraš direktno prop)
const localForm = ref({
  title: '',
  description: '',
  dueDate: '',
  priority: '',
  status: '',
})

// da li je edit
const isEditMode = computed(() => !!props.task)

// sync kada se otvori modal ili promeni task
watch(
  () => props.task,
  (task) => {
    if (task) {
      localForm.value = {
        title: task.title || '',
        description: task.description || '',
        dueDate: task.dueDate || '',
        priority: task.priority || '',
        status: task.status || '',
      }
    } else {
      localForm.value = {
        title: '',
        description: '',
        dueDate: dayjs().format('DD.MM.YYYY.'),
        priority: 'LOW',
        status: 'OPEN',
      }
    }
  },
  { immediate: true },
)

watch(
  () => props.modelValue,
  (isOpen) => {
    if (isOpen && props.task) {
      localForm.value.dueDate = dayjs(props.task.dueDate, 'YYYY-MM-DD').format('DD.MM.YYYY.') //props.task.dueDate
    }
  },
)

// close modal
const close = () => {
  emit('update:modelValue', false)
}

// submit
const handleSubmit = () => {
  const raw = localForm.value.dueDate
  let formattedDate

  if (!raw) {
    formattedDate = dayjs(dueDatePlaceholder.value, 'DD.MM.YYYY.').format('YYYY-MM-DD')
  } else if (dayjs(raw, 'YYYY-MM-DD', true).isValid()) {
    formattedDate = raw // već je u ispravnom formatu
  } else {
    formattedDate = dayjs(raw, 'DD.MM.YYYY.').format('YYYY-MM-DD')
  }

  emit('submit', {
    id: isEditMode.value ? props.task.id : null,
    title: localForm.value.title,
    description: localForm.value.description,
    dueDate: formattedDate,
    priority: localForm.value.priority ? localForm.value.priority : 'MEDIUM',
    status: localForm.value.status,
  })

  localForm.value = {
    title: '',
    description: '',
    dueDate: dayjs().format('DD.MM.YYYY.'),
    priority: 'LOW',
    status: 'OPEN',
  }
  close()
}

watch(
  () => props.modelValue,
  (val) => {
    //console.log('props.modelValue: ', val)
  },
)

watch(
  () => props.isEditMode,
  (val) => {
    //console.log('isEditMode.value: ', val)
  },
)

onMounted(() => {
  //console.log('Modal - onMounted: ' + JSON.stringify(props.task))
})
</script>

<style>
.vtd-datepicker-date.bg-vtd-primary-500 {
  background-color: #abadb1 !important;
  color: black !important;
  font-weight: 700;
  width: 1.8rem !important;
  height: 1.8rem !important;
  position: absolute !important;
  top: 50% !important;
  left: 80% !important;
  transform: translate(-50%, -50%) !important;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.7);
}
</style>
