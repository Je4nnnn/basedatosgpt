<template>
  <div class="finalizar-cita">
    <h1>Finalizar Cita</h1>
    <div v-if="loading">Procesando…</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <p>¿Seguro que deseas finalizar la cita <strong>#{{ id }}</strong>?</p>
      <button @click="finalizar">Sí, finalizar</button>
      <button @click="$router.back()">Cancelar</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route  = useRoute()
const router = useRouter()
const id      = route.params.id
const loading = ref(false)
const error   = ref(null)

async function finalizar() {
  loading.value = true
  error.value   = null
  try {
    const email = localStorage.getItem('usuarioEmail')
    await axios.post(
      `http://localhost:8081/api/medico/citas/finalizar`,
      null,
      { params: { email, idCita: id } }
    )
    alert('Cita finalizada correctamente')
    router.push({ name: 'Medico' })
  } catch (e) {
    error.value = e.response?.data || e.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.finalizar-cita {
  max-width: 400px;
  margin: 2rem auto;
  text-align: center;
}
button { margin: 0 .5rem; }
.error { color: red; }
</style>
