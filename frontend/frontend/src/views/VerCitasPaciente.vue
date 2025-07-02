<template>
  <div class="container">
    <h2>Tus citas médicas</h2>
    <ul v-if="citas.length">
      <li v-for="cita in citas" :key="cita.id">
        <strong>Fecha:</strong> {{ formatDate(cita.fechaHora) }}<br />
        <strong>Motivo:</strong> {{ cita.motivo }}<br />
        <strong>Médico:</strong> {{ cita.medico.nombre }}<br />
        <strong>Estado:</strong> {{ cita.estado }}
      </li>
    </ul>
    <p v-else>No tienes citas agendadas.</p>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      citas: [],
    }
  },
  async created() {
    const pacienteId = localStorage.getItem('usuarioId')

    try {
      const res = await axios.get(`http://localhost:8081/api/paciente/citas?pacienteId=${pacienteId}`)
      this.citas = res.data
    } catch (error) {
      console.error('Error al obtener citas médicas:', error)
    }
  },
  methods: {
    formatDate(fecha) {
      return new Date(fecha).toLocaleString()
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 700px;
  margin: auto;
  padding: 20px;
}
li {
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 10px;
}
</style>
