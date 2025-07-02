<!-- frontend/src/views/VerRecetas.vue -->
<template>
  <div class="ver-recetas">
    <h1>Recetas Pendientes</h1>
    <div v-if="loading">Cargando recetas…</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <ul v-else>
      <li v-for="r in recetas" :key="r.id">
        <strong>#{{ r.id }}</strong>
        – {{ formatDate(r.fechaEmision) }}
        – {{ r.paciente.nombre }}
        <button @click="confirmarEntrega(r.id)">OK</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const recetas = ref([])
const loading = ref(false)
const error   = ref(null)

function formatDate(d) {
  return new Date(d).toLocaleDateString()
}

async function fetchRecetas() {
  loading.value = true
  error.value   = null
  try {
    const email = encodeURIComponent(localStorage.getItem('usuarioEmail'))
    const resp  = await axios.get(
      `http://localhost:8081/api/recetas?email=${email}`
    )
    recetas.value = resp.data
  } catch (e) {
    error.value = e.response?.data || e.message
  } finally {
    loading.value = false
  }
}

async function confirmarEntrega(id) {
  if (!confirm(`¿Entregar receta #${id}?`)) return
  try {
    const email = encodeURIComponent(localStorage.getItem('usuarioEmail'))
    await axios.delete(
      `http://localhost:8081/api/recetas/${id}?email=${email}`
    )
    fetchRecetas()
  } catch (e) {
    alert('Error al confirmar entrega: ' + (e.response?.data || e.message))
  }
}

onMounted(fetchRecetas)
</script>

<style scoped>
.ver-recetas {
  max-width: 600px;
  margin: 2rem auto;
}
.error { color: red; }
ul { list-style: none; padding: 0; }
li { margin-bottom: 1rem; }
button { margin-left: 1rem; padding: .25rem .5rem; }
</style>
