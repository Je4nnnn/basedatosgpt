<template>
  <div class="container">
    <div class="header">
      <button @click="volver" class="btn-volver">← Volver a médicos</button>
      <h2>Disponibilidad de Dr. {{ nombreMedico }}</h2>
      <p class="especialidad-info">Especialidad: {{ especialidadNombre }}</p>
    </div>

    <div v-if="loading" class="loading">
      Cargando disponibilidad...
    </div>

    <div v-else-if="disponibilidades.length === 0" class="no-disponibilidad">
      <p>No hay horarios disponibles para este médico en este momento.</p>
      <button @click="volver" class="btn-secondary">Elegir otro médico</button>
    </div>

    <div v-else class="disponibilidades">
      <h3>Horarios disponibles:</h3>
      <div class="horarios-grid">
        <div
          v-for="disp in disponibilidades"
          :key="disp.id"
          class="horario-card"
        >
          <div class="horario-info">
            <div class="fecha">{{ formatFecha(disp.fechaHoraInicio) }}</div>
            <div class="hora">{{ formatHora(disp.fechaHoraInicio) }} - {{ formatHora(disp.fechaHoraFin) }}</div>
          </div>
          <button @click="irAgendar(disp)" class="btn-agendar">
            Agendar esta hora
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

const route = useRoute()
const router = useRouter()

const disponibilidades = ref([])
const loading = ref(true)
const nombreMedico = ref(decodeURIComponent(route.query.nombre || ''))
const especialidad = ref(route.query.especialidad || '')

const especialidadNombre = computed(() => {
  const especialidades = {
    'CARDIOLOGIA': 'Cardiología',
    'DERMATOLOGIA': 'Dermatología',
    'NEUROLOGIA': 'Neurología',
    'PEDIATRIA': 'Pediatría',
    'MEDICINA_GENERAL': 'Medicina General',
    'GINECOLOGIA': 'Ginecología',
    'TRAUMATOLOGIA': 'Traumatología'
  }
  return especialidades[especialidad.value] || especialidad.value
})

onMounted(async () => {
  const medicoId = route.params.id

  try {
    const response = await axios.get(
      `http://localhost:8081/api/paciente/disponibilidad?medicoId=${medicoId}`
    )
    disponibilidades.value = response.data.filter(d => d.disponible)
  } catch (error) {
    console.error('Error al obtener disponibilidad:', error)
    alert('Error al cargar la disponibilidad. Por favor, intenta nuevamente.')
  } finally {
    loading.value = false
  }
})

const formatFecha = (fecha) => {
  return new Date(fecha).toLocaleDateString('es-CL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const formatHora = (fecha) => {
  return new Date(fecha).toLocaleTimeString('es-CL', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

const volver = () => {
  router.push({
    path: '/paciente/medicos-especialidad',
    query: { especialidad: especialidad.value }
  })
}

const irAgendar = (disp) => {
  const medicoId = route.params.id
  router.push({
    path: `/paciente/agendar/${medicoId}`,
    query: {
      nombre: nombreMedico.value,
      fechaHoraInicio: disp.fechaHoraInicio,
      especialidad: especialidad.value
    }
  })
}
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.header {
  margin-bottom: 2rem;
}

.btn-volver {
  background: #95a5a6;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 1rem;
  font-size: 0.9rem;
  transition: background-color 0.3s ease;
}

.btn-volver:hover {
  background: #7f8c8d;
}

.container h2 {
  color: #2c3e50;
  margin: 0;
  font-size: 1.8rem;
}

.especialidad-info {
  color: #3498db;
  font-weight: 600;
  margin: 0.5rem 0 0 0;
  font-size: 1.1rem;
}

.loading {
  text-align: center;
  padding: 3rem;
  font-size: 1.1rem;
  color: #7f8c8d;
}

.no-disponibilidad {
  text-align: center;
  padding: 3rem;
}

.no-disponibilidad p {
  font-size: 1.1rem;
  color: #7f8c8d;
  margin-bottom: 1.5rem;
}

.btn-secondary {
  background: #95a5a6;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.btn-secondary:hover {
  background: #7f8c8d;
}

.disponibilidades h3 {
  color: #2c3e50;
  margin-bottom: 1.5rem;
  font-size: 1.4rem;
}

.horarios-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1rem;
}

.horario-card {
  background: white;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.horario-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.horario-info {
  margin-bottom: 1rem;
}

.fecha {
  font-weight: 600;
  color: #2c3e50;
  font-size: 1rem;
  margin-bottom: 0.5rem;
  text-transform: capitalize;
}

.hora {
  color: #3498db;
  font-size: 1.1rem;
  font-weight: 500;
}

.btn-agendar {
  background: #27ae60;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 500;
  width: 100%;
  transition: background-color 0.3s ease;
}

.btn-agendar:hover {
  background: #219a52;
}

.btn-agendar:active {
  transform: translateY(1px);
}

/* Responsive design */
@media (max-width: 768px) {
  .container {
    margin: 1rem auto;
    padding: 0 0.5rem;
  }

  .horarios-grid {
    grid-template-columns: 1fr;
  }

  .container h2 {
    font-size: 1.5rem;
  }

  .horario-card {
    padding: 1rem;
  }
}
</style>
