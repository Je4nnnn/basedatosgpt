<template>
  <div class="reprogramar-cita">
    <div class="header">
      <h1>Reprogramar Cita</h1>
    </div>

    <div v-if="loading" class="loading">Cargando citas…</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else-if="citas.length === 0" class="no-citas">
      <p>No tienes citas para reprogramar.</p>
    </div>

    <div v-else class="citas-list">
      <div v-for="c in citas" :key="c.id" class="cita-card">
        <div class="cita-info">
          <div class="cita-header">
            <h3>Dr. {{ c.medico.nombre }} {{ c.medico.apellido }}</h3>
            <span class="especialidad">{{ c.medico.especialidad }}</span>
          </div>
          <div class="cita-detalles">
            <div class="fecha-hora">
              {{ formatDateTime(c.fechaHora) }}
            </div>
            <div class="motivo" v-if="c.motivo">
              {{ c.motivo }}
            </div>
          </div>
        </div>
        <div class="cita-acciones">
          <button @click="reprogramar(c)" class="btn-reprogramar">
            Reprogramar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Props y emits
const emit = defineEmits(['volver', 'navegarAEspecialidad'])

const citas = ref([])
const loading = ref(true)
const error = ref(null)

function formatDateTime(dt) {
  return new Date(dt).toLocaleString('es-CL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// Mapeo de especialidades para convertir de formato DB a formato URL
function getEspecialidadKey(especialidadDB) {
  const especialidadesMap = {
    'Cardiología': 'CARDIOLOGIA',
    'Dermatología': 'DERMATOLOGIA',
    'Neurología': 'NEUROLOGIA',
    'Pediatría': 'PEDIATRIA',
    'Medicina General': 'MEDICINA_GENERAL',
    'Ginecología': 'GINECOLOGIA',
    'Traumatología': 'TRAUMATOLOGIA'
  }
  return especialidadesMap[especialidadDB] || especialidadDB
}

async function fetchCitas() {
  loading.value = true
  error.value = null
  try {
    const email = localStorage.getItem('usuarioEmail')
    if (!email) {
      error.value = 'No se encontró información de usuario'
      emit('volver')
      return
    }

    const resp = await axios.get(
      `http://localhost:8081/api/paciente/citas?email=${encodeURIComponent(email)}`
    )
    // Solo mostrar citas futuras que se pueden reprogramar
    const now = new Date()
    citas.value = resp.data.filter(cita => new Date(cita.fechaHora) > now)
  } catch (e) {
    console.error('Error al cargar citas:', e)
    error.value = e.response?.data?.error || e.message
  } finally {
    loading.value = false
  }
}

async function reprogramar(cita) {
  if (!confirm('¿Estás seguro de que deseas reprogramar esta cita? La cita actual será cancelada.')) {
    return
  }

  try {
    const email = localStorage.getItem('usuarioEmail')

    // 1) Cancelamos la cita actual
    await axios.delete(
      `http://localhost:8081/api/paciente/citas/${cita.id}`,
      { params: { email } }
    )

    // 2) Emitimos evento para navegar a la selección de especialidad
    const especialidadKey = getEspecialidadKey(cita.medico.especialidad)

    emit('navegarAEspecialidad', {
      especialidad: especialidadKey,
      reprogramando: true,
      motivoOriginal: cita.motivo || '',
      citaOriginalId: cita.id
    })

    // Mostrar mensaje informativo
    alert('Cita cancelada. Ahora selecciona un médico y horario para reprogramar.')

  } catch (e) {
    console.error('Error al reprogramar:', e)
    alert('Error al cancelar la cita: ' + (e.response?.data?.error || e.message))
  }
}

onMounted(fetchCitas)
</script>

<style scoped>
.reprogramar-cita {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  min-height: 100vh;
}

.header {
  margin-bottom: 2rem;
  text-align: center;
}

.reprogramar-cita h1 {
  color: white;
  margin: 0;
  font-size: 2.5rem;
  font-weight: 300;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.loading {
  text-align: center;
  padding: 4rem;
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.8);
}

.error {
  color: #ff6b6b;
  background: rgba(255, 107, 107, 0.1);
  border: 1px solid rgba(255, 107, 107, 0.3);
  padding: 1.5rem;
  border-radius: 10px;
  text-align: center;
  font-weight: 500;
  backdrop-filter: blur(10px);
}

.no-citas {
  text-align: center;
  padding: 4rem;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 15px;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.no-citas p {
  font-size: 1.2rem;
  color: rgba(255, 255, 255, 0.9);
  margin-bottom: 2rem;
}

.citas-list {
  display: grid;
  gap: 1.5rem;
}

.cita-card {
  background: #4b5563;
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 15px;
  padding: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.3s ease;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.cita-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
  border-color: rgba(255, 255, 255, 0.4);
  background: #556171;
}

.cita-info {
  flex: 1;
}

.cita-header {
  margin-bottom: 1rem;
}

.cita-header h3 {
  color: white;
  margin: 0 0 0.5rem 0;
  font-size: 1.3rem;
  font-weight: 600;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.especialidad {
  background: #6b7280;
  color: white;
  padding: 0.4rem 1rem;
  border-radius: 20px;
  font-size: 0.85rem;
  font-weight: 500;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.cita-detalles {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.fecha-hora,
.motivo {
  color: rgba(255, 255, 255, 0.85);
  font-size: 1rem;
}

.cita-acciones {
  flex-shrink: 0;
  margin-left: 2rem;
}

.btn-reprogramar {
  background: #A94438;
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.2);
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  font-size: 0.95rem;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  transition: all 0.3s ease;
}

.btn-reprogramar:hover {
  background: #8A3235;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(169, 68, 56, 0.4);
}

.btn-reprogramar:active {
  transform: translateY(0);
}

/* Responsive design */
@media (max-width: 768px) {
  .reprogramar-cita {
    padding: 1rem;
  }

  .reprogramar-cita h1 {
    font-size: 2rem;
  }

  .cita-card {
    flex-direction: column;
    align-items: stretch;
    gap: 1.5rem;
    padding: 1.5rem;
  }

  .cita-acciones {
    margin-left: 0;
  }

  .btn-reprogramar {
    width: 100%;
    justify-content: center;
  }

  .cita-detalles {
    gap: 1rem;
  }

  .fecha-hora,
  .motivo {
    font-size: 0.95rem;
  }
}

@media (max-width: 480px) {
  .reprogramar-cita {
    padding: 0.5rem;
  }

  .cita-card {
    padding: 1rem;
  }

  .cita-header h3 {
    font-size: 1.1rem;
  }

  .especialidad {
    font-size: 0.8rem;
    padding: 0.3rem 0.8rem;
  }
}
</style>