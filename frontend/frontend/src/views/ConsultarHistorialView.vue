<template>
  <div class="p-6">
    <h1 class="text-3xl font-bold mb-6">Mi Historial Médico</h1>

    <div v-if="historial.length === 0" class="text-gray-600">
      No tienes historial médico registrado.
    </div>

    <div v-else class="space-y-4">
      <div v-for="registro in historial" :key="registro.id" class="border p-4 rounded-lg shadow">
        <p><strong>Diagnóstico:</strong> {{ registro.diagnostico }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const historial = ref([]);

const fetchHistorial = async () => {
  try {
    const pacienteId = localStorage.getItem('userId');
    const response = await axios.get(`http://localhost:8081/historial/paciente/${pacienteId}`);
    historial.value = response.data;
  } catch (error) {
    console.error('Error al obtener el historial médico:', error);
  }
};

onMounted(fetchHistorial);
</script>

<style scoped>
</style>
