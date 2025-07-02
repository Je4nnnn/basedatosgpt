<template>
  <div class="medicos-especialidad">
    <div class="header">
      <button @click="volver" class="btn-volver">← Volver a especialidades</button>
      <h1>Médicos disponibles para {{ especialidadNombre }}</h1>
    </div>

    <div v-if="loading" class="loading">
      Cargando médicos...
    </div>

    <div v-else-if="medicos.length === 0" class="no-medicos">
      <p>No hay médicos disponibles para esta especialidad en este momento.</p>
      <button @click="volver" class="btn-secondary">Elegir otra especialidad</button>
    </div>

    <div v-else class="medicos-grid">
      <div
        v-for="medico in medicos"
        :key="medico.id"
        class="medico-card"
      >
        <div class="medico-info">
          <h3>Dr. {{ medico.nombre }}</h3>
          <p class="medico-email">{{ medico.email }}</p>
          <p class="especialidad-badge">{{ especialidadNombre }}</p>
        </div>
        <button
          @click="verDisponibilidad(medico.id, medico.nombre)"
          class="btn-primary"
        >
          Ver disponibilidad
        </button>
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

const medicos = ref([])
const loading = ref(true)
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
  if (!especialidad.value) {
    router.push('/paciente/especialidades')
    return
  }

  try {
    const response = await axios.get(
      `http://localhost:8081/api/paciente/medicos/especialidad/${especialidad.value}`
    )
    medicos.value = response.data
  } catch (error) {
    console.error('Error al cargar médicos:', error)
    alert('Error al cargar los médicos. Por favor, intenta nuevamente.')
  } finally {
    loading.value = false
  }
})

const volver = () => {
  router.push('/paciente/especialidades')
}

const verDisponibilidad = (medicoId, nombreMedico) => {
  router.push({
    path: `/paciente/disponibilidad/${medicoId}`,
    query: {
      nombre: nombreMedico,
      especialidad: especialidad.value
    }
  })
}
</script>

<style scoped>
.medicos-especialidad {
  max-width: 1200px;
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
}

.btn-volver:hover {
  background: #7f8c8d;
}

.medicos-especialidad h1 {
  color: #2c3e50;
  text-align: center;
  margin: 0;
}

.loading {
  text-align: center;
  padding: 3rem;
  font-size: 1.1rem;
  color: #7f8c8d;
}

.no-medicos {
  text-align: center;
  padding: 3rem;
}

.no-medicos p {
  font-size: 1.1rem;
  color: #7f8c8d;
  margin-bottom: 1.5rem;
}

.medicos-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 1.5rem;
}

.medico-card {
  background: white;
  border: 1px solid #ecf0f1;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease;
}

.medico-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}

.medico-info {
  margin-bottom: 1rem;
}

.medico-info h3 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
}

.medico-email {
  color: #7f8c8d;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.especialidad-badge {
  display: inline-block;
  background: #3498db;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 500;
}

.btn-primary {
  width: 100%;
  background: #3498db;
  color: white;
  border: none;
  padding: 0.75rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s ease;
}

.btn-primary:hover {
  background: #2980b9;
}

.btn-secondary {
  background: #95a5a6;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
}

.btn-secondary:hover {
  background: #7f8c8d;
}

@media (max-width: 768px) {
  .medicos-grid {
    grid-template-columns: 1fr;
  }

  .medico-card {
    padding: 1rem;
  }
}
</style>