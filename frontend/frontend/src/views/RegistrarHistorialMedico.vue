<template>
  <div class="form-container">
    <h1>Registrar Historial Médico</h1>
    <form @submit.prevent="registrar">
      <div class="field">
        <label>Paciente ID:</label>
        <input type="text" v-model="pacienteId" readonly />
      </div>
      <div class="field">
        <label>Diagnóstico:</label>
        <textarea v-model="diagnostico" required></textarea>
      </div>
      <button type="submit">Guardar Historial</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route       = useRoute()
const router      = useRouter()
const pacienteId  = ref(route.query.pacienteId)
const diagnostico = ref('')

async function registrar() {
  try {
    const email = localStorage.getItem('usuarioEmail')
    await axios.post(
      `http://localhost:8081/api/medico/historial`,
      {
        emailMedico: email,
        pacienteId:  parseInt(pacienteId.value),
        diagnostico: diagnostico.value
      }
    )
    alert('Historial registrado correctamente')
    router.push({ name: 'VerCitasMedico' })
  } catch (e) {
    alert('Error al registrar historial: ' + (e.response?.data || e.message))
  }
}
</script>

<style scoped>
.form-container { max-width: 400px; margin: 2rem auto; }
.field          { margin-bottom: 1rem; }
label           { display: block; margin-bottom: .5rem; }
input, textarea { width: 100%; padding: .5rem; box-sizing: border-box; }
button          { padding: .75rem 1.5rem; background: #007b00; color: white; border: none; cursor: pointer; }
</style>
