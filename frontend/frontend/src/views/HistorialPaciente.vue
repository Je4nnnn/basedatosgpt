<template>
  <div>
    <h1>Mi Historial Médico</h1>
    <ul>
      <li v-for="registro in historial" :key="registro.id">
        {{ registro.diagnostico }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HistorialPaciente',
  data() {
    return {
      historial: []
    }
  },
  mounted() {
    this.obtenerHistorial();
  },
  methods: {
    async obtenerHistorial() {
      const pacienteId = localStorage.getItem('idUsuario');
      if (!pacienteId) {
        alert('No se encontró el ID del paciente');
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/api/paciente/historial/${pacienteId}`);
        this.historial = response.data;
      } catch (error) {
        console.error(error);
        alert('Error al obtener historial médico.');
      }
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
  margin-top: 30px;
}
ul {
  list-style: none;
  padding: 0;
}
li {
  margin: 10px 0;
}
</style>
