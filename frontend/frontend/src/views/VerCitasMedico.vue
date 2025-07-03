<!-- frontend/src/views/VerCitasMedico.vue -->
<template>
  <div class="ver-citas-medico">
    <h1>Mis Citas Asignadas</h1>

    <div v-if="loading">Cargando citas…</div>
    <div v-else-if="error" class="error">{{ error }}</div>

    <ul v-else>
      <li v-for="c in citas" :key="c.id">
        <strong>ID:</strong> {{ c.id }} —
        {{ formatDateTime(c.fechaHora) }} —
        Paciente: {{ c.pacienteNombre }}

        <button @click="finalizar(c.id)">Finalizar</button>
        <button @click="irHistorial(c.pacienteId)">Registrar Historial</button>
        <button @click="irReceta(c.pacienteId)">Registrar Receta</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const citas   = ref([])
const loading = ref(false)
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
    const { data } = await axios.get('/api/medico/citas', {
      params: { email }
    })
    // data es un array de objetos con { id, fechaHora, motivo, especialidad, estado, pacienteId, pacienteNombre }
    citas.value = data
  } catch (e) {
    // muestra bien el mensaje de error
    const d = e.response?.data
    error.value = d?.error || d?.message || JSON.stringify(d) || e.message
  } finally {
    loading.value = false
  }
}

async function finalizar(citaId) {
  try {
    const email = localStorage.getItem('usuarioEmail')
    // PUT con params
    await axios.put('/api/medico/finalizar-cita', null, {
      params: { email, citaId }
    })
    alert('Consulta finalizada exitosamente')
    fetchCitas()
  } catch (e) {
    const d = e.response?.data
    const msg = d?.error || d?.message || JSON.stringify(d) || e.message
    alert('Error al finalizar cita: ' + msg)
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
.ver-citas-medico {
  max-width: 800px;
  margin: 2rem auto;
}
.error {
  color: red;
  margin-bottom: 1rem;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  margin-bottom: 1rem;
}
button {
  margin-left: 0.5rem;
}
</style>
