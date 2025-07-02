<template>
  <div class="ver-citas-medico">
    <h1>Mis Citas Asignadas</h1>
    <div v-if="loading">Cargando citas…</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <ul v-else>
      <li v-for="c in citas" :key="c.id">
        <strong>ID:</strong> {{ c.id }} —
        {{ formatDateTime(c.fechaHora) }} —
        Paciente: {{ c.paciente.nombre }}
        <button @click="finalizar(c.id)">Finalizar</button>
        <button @click="irHistorial(c.paciente.id)">Registrar Historial</button>
        <button @click="irReceta(c.paciente.id)">Registrar Receta</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const citas   = ref([])
const loading = ref(true)
const error   = ref(null)
const router  = useRouter()

function formatDateTime(dt) {
  return new Date(dt).toLocaleString()
}

async function fetchCitas() {
  loading.value = true
  error.value   = null
  try {
    const email = localStorage.getItem('usuarioEmail')
    const resp  = await axios.get(
      `http://localhost:8081/api/medico/citas?email=${encodeURIComponent(email)}`
    )
    citas.value = resp.data
  } catch (e) {
    error.value = e.response?.data || e.message
  } finally {
    loading.value = false
  }
}

async function finalizar(citaId) {
  try {
    const email = localStorage.getItem('usuarioEmail')
    await axios.put(
      `http://localhost:8081/api/medico/finalizar-cita?email=${encodeURIComponent(email)}&citaId=${citaId}`
    )
    alert('Consulta finalizada exitosamente')
    fetchCitas()
  } catch (e) {
    alert('Error al finalizar cita: ' + (e.response?.data || e.message))
  }
}

function irHistorial(pacienteId) {
  router.push({ name: 'RegistrarHistorialMedico', query: { pacienteId } })
}

function irReceta(pacienteId) {
  router.push({ name: 'RegistrarReceta', query: { pacienteId } })
}

onMounted(fetchCitas)
</script>

<style scoped>
.error { color: red; }
.ver-citas-medico { max-width: 800px; margin: 2rem auto; }
ul { list-style: none; padding: 0; }
li { margin-bottom: 1rem; }
button { margin-left: 0.5rem; }
</style>
