<template>
  <div class="container">
    <div class="header">
      <button @click="volver" class="btn-volver">← Volver a especialidades</button>

      <!-- Mensaje especial cuando se está reprogramando -->
      <div v-if="esReprogramacion" class="reprogramacion-alert">
        <i class="fas fa-info-circle"></i>
        <span>Reprogramando cita - Selecciona un nuevo médico y horario</span>
      </div>

      <h2>Médicos de {{ especialidadNombre }}</h2>
      <p class="subtitle">Selecciona un médico para ver su disponibilidad</p>
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
        @click="verDisponibilidad(medico)"
      >
        <div class="medico-avatar">
          <i class="fas fa-user-md"></i>
        </div>
        <div class="medico-info">
          <h3>Dr. {{ medico.nombre }} {{ medico.apellido }}</h3>
          <p class="especialidad">{{ especialidadNombre }}</p>
          <p class="experiencia" v-if="medico.experiencia">
            {{ medico.experiencia }} años de experiencia
          </p>
          <div class="medico-detalles">
            <span class="horario" v-if="medico.horarioAtencion">
              <i class="fas fa-clock"></i>
              {{ medico.horarioAtencion }}
            </span>
            <span class="ubicacion" v-if="medico.consultorio">
              <i class="fas fa-map-marker-alt"></i>
              Consultorio {{ medico.consultorio }}
            </span>
          </div>
        </div>
        <div class="medico-arrow">
          <i class="fas fa-chevron-right"></i>
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

const medicos = ref([])
const loading = ref(true)
const especialidad = ref(route.query.especialidad || '')

// Verificar si estamos en modo reprogramación
const esReprogramacion = computed(() => route.query.reprogramando === 'true')

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

// Mapeo para enviar a la base de datos (valores que coinciden con PostgreSQL)
const especialidadParaDB = computed(() => {
  const especialidadesDB = {
    'CARDIOLOGIA': 'Cardiología',
    'DERMATOLOGIA': 'Dermatología',
    'NEUROLOGIA': 'Neurología',
    'PEDIATRIA': 'Pediatría',
    'MEDICINA_GENERAL': 'Medicina General',
    'GINECOLOGIA': 'Ginecología',
    'TRAUMATOLOGIA': 'Traumatología'
  }
  return especialidadesDB[especialidad.value] || especialidad.value
})

onMounted(async () => {
  console.log('Especialidad original:', especialidad.value)
  console.log('Especialidad para DB:', especialidadParaDB.value)

  // 🔥 CAMBIO: Usar la URL correcta que coincide con el controlador
  const url = `http://localhost:8081/api/paciente/medicos/especialidad/${encodeURIComponent(especialidadParaDB.value)}`
  console.log('URL completa:', url)

  try {
    const response = await axios.get(url)
    console.log('Respuesta de la API:', response.data)
    medicos.value = response.data
  } catch (error) {
    console.error('Error al obtener médicos:', error)
    console.error('Error response:', error.response)
    alert('Error al cargar los médicos. Por favor, intenta nuevamente.')
  } finally {
    loading.value = false
  }
})

const verDisponibilidad = (medico) => {
  // Pasar parámetros de reprogramación si aplica
  const query = {
    nombre: `${medico.nombre} ${medico.apellido}`,
    especialidad: especialidad.value
  }

  // Si estamos reprogramando, pasar los datos adicionales
  if (esReprogramacion.value) {
    query.reprogramando = 'true'
    query.motivoOriginal = route.query.motivoOriginal || ''
    query.citaOriginalId = route.query.citaOriginalId || ''
  }

  router.push({
    path: `/paciente/disponibilidad/${medico.id}`,
    query: query
  })
}

const volver = () => {
  // Si estamos reprogramando, volver a las citas
  if (esReprogramacion.value) {
    router.push('/paciente/ver-citas')
  } else {
    router.push('/paciente/especialidades')
  }
}
</script>

<style scoped>
.container {
  max-width: 900px;
  margin: 2rem auto;
  padding: 0 1rem;
}

.header {
  margin-bottom: 2rem;
}

.reprogramacion-alert {
  background: #fff3cd;
  border: 1px solid #ffeaa7;
  color: #856404;
  padding: 0.75rem 1rem;
  border-radius: 6px;
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.reprogramacion-alert i {
  color: #f39c12;
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

.subtitle {
  color: #7f8c8d;
  margin: 0.5rem 0 0 0;
  font-size: 1rem;
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

.medicos-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

.medico-card {
  background: white;
  border: 1px solid #e9ecef;
  border-radius: 10px;
  padding: 1.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 1.5rem;
  position: relative;
}

.medico-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  border-color: #3498db;
}

.medico-avatar {
  background: #3498db;
  color: white;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  flex-shrink: 0;
}

.medico-info {
  flex: 1;
}

.medico-info h3 {
  color: #2c3e50;
  margin: 0 0 0.5rem 0;
  font-size: 1.2rem;
  font-weight: 600;
}

.especialidad {
  color: #3498db;
  font-weight: 500;
  margin: 0 0 0.5rem 0;
  font-size: 0.95rem;
}

.experiencia {
  color: #7f8c8d;
  margin: 0 0 1rem 0;
  font-size: 0.9rem;
}

.medico-detalles {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.horario,
.ubicacion {
  color: #7f8c8d;
  font-size: 0.85rem;
  display: flex;
  align-items: center;
  gap: 0.3rem;
}

.horario i,
.ubicacion i {
  color: #95a5a6;
}

.medico-arrow {
  color: #3498db;
  font-size: 1.2rem;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.medico-card:hover .medico-arrow {
  opacity: 1;
}

/* Responsive design */
@media (max-width: 768px) {
  .container {
    margin: 1rem auto;
    padding: 0 0.5rem;
  }

  .medico-card {
    padding: 1rem;
    gap: 1rem;
  }

  .medico-avatar {
    width: 50px;
    height: 50px;
    font-size: 1.2rem;
  }

  .medico-info h3 {
    font-size: 1.1rem;
  }

  .medico-detalles {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>