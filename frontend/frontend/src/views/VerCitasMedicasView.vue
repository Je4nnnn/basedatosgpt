<template>
  <div class="p-6">
    <h1 class="text-3xl font-bold mb-6">Mis Citas Médicas</h1>

    <div v-if="citas.length === 0" class="text-gray-600">
      No tienes citas médicas agendadas.
    </div>

    <div v-else class="space-y-4">
      <div v-for="cita in citas" :key="cita.id" class="border p-4 rounded-lg shadow">
        <p><strong>Médico:</strong> {{ cita.medicoNombre }}</p>
        <p><strong>Fecha y Hora:</strong> {{ formatFecha(cita.fechaHora) }}</p>
        <p><strong>Motivo:</strong> {{ cita.motivo }}</p>
        <p><strong>Estado:</strong> {{ cita.estado }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const citas = ref([]);

const fetchCitas = async () => {
  try {
    const pacienteId = localStorage.getItem('userId');
    const response = await axios.get(`http://localhost:8081/citas/paciente/${pacienteId}`);
    citas.value = response.data;
  } catch (error) {
    console.error('Error al obtener las citas médicas:', error);
  }
};

const formatFecha = (fechaHora) => {
  return new Date(fechaHora).toLocaleString();
};

onMounted(fetchCitas);
</script>

<style scoped>
</style>
