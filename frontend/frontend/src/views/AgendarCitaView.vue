<template>
  <div class="agendar-cita">
    <h1>Agendar Cita</h1>
    <div>
      <label for="motivo">Motivo de la consulta:</label>
      <input
        id="motivo"
        v-model="motivo"
        type="text"
        placeholder="Ingrese el motivo de la consulta"
      />
    </div>
    <button @click="agendarCita">Confirmar Cita</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// Tomamos médico y fecha desde query params
const medicoId   = ref(route.query.medicoId || '')
const fechaHora  = ref(route.query.fechaHoraInicio || '')
const motivo     = ref('')

const agendarCita = async () => {
  try {
    // Recuperamos el email del paciente guardado en localStorage
    const email = localStorage.getItem('usuarioEmail')
    if (!email) throw new Error('Usuario no autenticado')

    // Llamamos al backend correcto (puerto 8081) y enviamos JSON
    await axios.post(
      `http://localhost:8081/api/paciente/citas?email=${encodeURIComponent(email)}`,
      {
        medicoId: Number(medicoId.value),
        fechaHoraInicio: fechaHora.value,
        motivo: motivo.value
      }
    )

    alert('Cita agendada exitosamente')
    router.push('/paciente/ver-citas')
  } catch (error) {
    console.error(error)
    const msg = error.response?.data?.error || error.message
    alert('Error al agendar la cita:\n' + msg)
  }
}
</script>

<style scoped>
.agendar-cita {
  max-width: 400px;
  margin: 2rem auto;
}
.agendar-cita input {
  width: 100%;
  padding: 0.5rem;
  margin: 0.5rem 0;
}
.agendar-cita button {
  padding: 0.5rem 1rem;
}
</style>
