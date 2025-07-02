<template>
  <div class="registrar-receta">
    <h1>Registrar Receta</h1>
    <div v-if="loading">Cargando datos…</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <p><strong>Paciente:</strong> {{ paciente.nombre }}</p>
      <div class="field">
        <label>Medicamento:</label>
        <select v-model="nombreMedicamento">
          <option disabled value="">Seleccione un medicamento</option>
          <option v-for="m in medicamentos" :key="m.id" :value="m.nombre">
            {{ m.nombre }}
          </option>
        </select>
      </div>
      <div class="field">
        <label>Descripción (observaciones):</label>
        <textarea v-model="descripcion" placeholder="Escribe aquí…"></textarea>
      </div>
      <button @click="guardar">Guardar Receta</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route             = useRoute()
const router            = useRouter()
const pacienteId        = Number(route.query.pacienteId)
const email             = localStorage.getItem('usuarioEmail')
const medicamentos      = ref([])
const paciente          = ref({})
const nombreMedicamento = ref('')
const descripcion       = ref('')
const loading           = ref(true)
const error             = ref(null)

async function fetchData() {
  try {
    // 1) Obtener la cita para recuperar el paciente
    const rc = await axios.get(
      `http://localhost:8081/api/medico/citas?email=${encodeURIComponent(email)}`
    )
    const cita = rc.data.find(c => c.paciente.id === pacienteId)
    paciente.value = cita.paciente
    // 2) Listar todos los medicamentos
    const rm = await axios.get('http://localhost:8081/api/medicamentos')
    medicamentos.value = rm.data
  } catch (e) {
    error.value = e.response?.data || e.message
  } finally {
    loading.value = false
  }
}

async function guardar() {
  try {
    await axios.post(
      `http://localhost:8081/api/medico/recetas?email=${encodeURIComponent(email)}`,
      {
        nombreMedicamento: nombreMedicamento.value,
        descripcion:       descripcion.value,
        pacienteId:        pacienteId
      }
    )
    alert('Receta registrada correctamente')
    router.push({ name: 'VerCitasMedico' })
  } catch (e) {
    alert('Error al registrar receta: ' + (e.response?.data || e.message))
  }
}

onMounted(fetchData)
</script>

<style scoped>
.error { color: red; }
.registrar-receta { max-width: 600px; margin: 2rem auto; }
.field { margin-bottom: 1rem; }
select, textarea { width: 100%; padding: .5rem; box-sizing: border-box; }
button { padding: .75rem; }
</style>
