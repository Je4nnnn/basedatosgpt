<template>
  <div>
    <h1>Mis Citas Médicas</h1>
    <ul>
      <li v-for="cita in citas" :key="cita.id">
        {{ cita.fechaHora }} - {{ cita.motivo }} (Estado: {{ cita.estado }})
      </li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'MisCitas',
  data() {
    return {
      citas: []
    }
  },
  mounted() {
    this.obtenerCitas();
  },
  methods: {
    async obtenerCitas() {
      const pacienteId = localStorage.getItem('idUsuario');
      if (!pacienteId) {
        alert('No se encontró el ID del paciente');
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/api/paciente/citas/${pacienteId}`);
        this.citas = response.data;
      } catch (error) {
        console.error(error);
        alert('Error al obtener citas.');
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
