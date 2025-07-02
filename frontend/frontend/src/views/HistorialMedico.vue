<!-- src/views/HistorialMedico.vue -->
<template>
  <div class="historial-container">
    <h2>Mi Historial Médico</h2>

    <div v-if="loading">Cargando historial…</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <ul v-else-if="historial.length">
      <li v-for="h in historial" :key="h.id" class="entrada">
        <p><strong>Diagnóstico:</strong> {{ h.diagnostico }}</p>
        <!-- Si tu DTO tiene fecha:
        <p><strong>Fecha:</strong> {{ new Date(h.fecha).toLocaleString() }}</p>
        -->
      </li>
    </ul>

    <p v-else>No tienes historial médico registrado.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const historial = ref([])
const loading   = ref(true)
const error     = ref(null)

async function fetchHistorial() {
  loading.value = true
  error.value   = null

  const email = localStorage.getItem('usuarioEmail')
  if (!email) {
    error.value = 'No estás autenticado'
    loading.value = false
    return
  }

  try {
    const resp = await axios.get(
      `http://localhost:8081/api/paciente/historial?email=${encodeURIComponent(email)}`
    )
    historial.value = resp.data
  } catch (e) {
    error.value = e.response?.data || e.message
  } finally {
    loading.value = false
  }
}

onMounted(fetchHistorial)
</script>

<style scoped>
.historial-container { max-width: 600px; margin: 2rem auto; padding: 1rem; }
.error { color: red; }
.entrada { margin-bottom: 1rem; border-bottom: 1px solid #ccc; padding-bottom: .5rem; }
</style>
