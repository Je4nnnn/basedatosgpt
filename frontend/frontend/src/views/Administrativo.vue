<!-- frontend/src/views/Administrativo.vue -->
<template>
  <div class="administrativo-container">
    <h1>Panel Administrativo</h1>

    <nav>
      <ul>
        <li>
          <router-link :to="{ name: 'RegistrarMedicamento' }">
            Registrar Medicamento
          </router-link>
        </li>
        <li>
          <router-link :to="{ name: 'ListarMedicamentos' }">
            Ver Medicamentos
          </router-link>
        </li>
      </ul>
    </nav>

    <section class="excel-upload">
      <h2>Carga Masiva de Excel</h2>
      <input
        type="file"
        accept=".xlsx"
        @change="onFileChange"
      />
      <button
        :disabled="!file"
        @click="uploadFile"
      >
        Subir Archivo Excel
      </button>
      <p v-if="message" class="message">{{ message }}</p>
    </section>
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
  const formData = new FormData()
  formData.append('file', file.value)

  try {
    // Aquí apuntas directamente a tu backend en el puerto 8081
    const res = await axios.post(
      'http://localhost:8081/api/admin/upload',
      formData,
      { headers: { 'Content-Type': 'multipart/form-data' } }
    )
    message.value = res.data
  } catch (err) {
    message.value = err.response?.data || err.message
  }
}
</script>

<style scoped>
.administrativo-container {
  max-width: 600px;
  margin: 2rem auto;
}
nav ul {
  list-style: none;
  padding: 0;
}
nav li {
  margin-bottom: 0.5rem;
}
.excel-upload {
  margin-top: 2rem;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.excel-upload h2 {
  margin-bottom: 0.5rem;
}
.excel-upload input {
  display: block;
  margin-bottom: 0.5rem;
}
.excel-upload button {
  padding: 0.5rem 1rem;
  background-color: #38a169;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.excel-upload button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.message {
  margin-top: 0.75rem;
  color: #2d3748;
}
</style>
