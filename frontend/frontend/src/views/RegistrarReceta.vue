<!-- frontend/src/views/RegistrarReceta.vue -->
<template>
  <div class="registrar-receta">
    <h1>Registrar Receta</h1>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="success" class="success">{{ success }}</div>

    <form @submit.prevent="submit">
      <div class="field">
        <label for="medicamento">Nombre del Medicamento:</label>
        <input
          id="medicamento"
          v-model="nombreMedicamento"
          type="text"
          required
        />
      </div>

      <div class="field">
        <label for="desc">Descripción de la Receta:</label>
        <textarea
          id="desc"
          v-model="descripcion"
          required
        ></textarea>
      </div>

      <button type="submit">Registrar Receta</button>
      <button type="button" @click="volver">Cancelar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

// 1) Tomamos pacienteId desde el query param
const pacienteId = Number(route.query.pacienteId || 0)

const nombreMedicamento = ref('')
const descripcion       = ref('')
const error             = ref(null)
const success           = ref(null)

async function submit() {
  error.value   = null
  success.value = null

  if (!pacienteId) {
    error.value = 'Paciente inválido'
    return
  }

  try {
    const email = localStorage.getItem('usuarioEmail')

    // 2) Llamada POST a /api/medico/recetas con params y body
    const body = {
      nombreMedicamento: nombreMedicamento.value,
      descripcion:       descripcion.value,
      pacienteId
    }

    const resp = await axios.post(
      '/api/medico/recetas',
      body,
      { params: { email } }
    )

    success.value = 'Receta registrada con éxito (ID: ' + resp.data.id + ')'
    // opcional: redirigir tras 1s
    setTimeout(() => router.push({ name: 'VerCitasMedico' }), 1000)
  } catch (e) {
    // recogemos error de validación o mensaje genérico
    const d = e.response?.data
    error.value = d?.descripcion || d?.message || JSON.stringify(d) || e.message
  }
}

function volver() {
  router.back()
}
</script>

<style scoped>
.registrar-receta {
  max-width: 600px;
  margin: 2rem auto;
}
.field {
  margin-bottom: 1rem;
}
label {
  display: block;
  margin-bottom: .25rem;
  font-weight: bold;
}
input, textarea {
  width: 100%;
  padding: .5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  margin-right: .5rem;
  padding: .5rem 1rem;
  background-color: #3182ce;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button[type="button"] {
  background-color: #a0aec0;
}
.error { color: #e53e3e; margin-bottom: 1rem; }
.success { color: #38a169; margin-bottom: 1rem; }
</style>
