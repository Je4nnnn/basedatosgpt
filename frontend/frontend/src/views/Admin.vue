<template>
  <div class="p-4">
    <h1 class="text-2xl mb-4">Carga Masiva de Excels</h1>
    <input type="file" @change="onFileChange" accept=".xlsx" />
    <button
      class="mt-2 px-4 py-2 bg-blue-600 text-white rounded"
      :disabled="!file"
      @click="uploadFile"
    >
      Subir Archivo
    </button>
    <p v-if="message" class="mt-4">{{ message }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const file = ref(null)
const message = ref('')

function onFileChange(event) {
  file.value = event.target.files[0]
}

async function uploadFile() {
  if (!file.value) return
  const form = new FormData()
  form.append('file', file.value)
  try {
    const res = await axios.post('/api/admin/upload', form, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
    message.value = res.data
  } catch (err) {
    message.value = err.response?.data || err.message
  }
}
</script>

<style scoped>
/* Estilos simples */
</style>
