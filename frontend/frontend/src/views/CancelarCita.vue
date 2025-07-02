<template>
  <div class="cancelar-cita-container">
    <div class="header">
      <h2>Cancelar Cita Médica</h2>
      <p class="subtitle">Selecciona la cita que deseas cancelar</p>
    </div>

    <div v-if="loading" class="loading">
      Cargando tus citas...
    </div>

    <div v-else-if="citas.length === 0" class="no-citas">
      <div class="no-citas-icon">
        <i class="fas fa-calendar-times"></i>
      </div>
      <h3>No tienes citas programadas</h3>
      <p>No hay citas disponibles para cancelar en este momento.</p>
    </div>

    <div v-else class="citas-list">
      <div
        v-for="cita in citas"
        :key="cita.id"
        class="cita-card"
      >
        <div class="cita-info">
          <div class="cita-header">
            <h3>Dr. {{ cita.nombreMedico || cita.medico?.nombre || 'Médico no especificado' }}</h3>
            <div class="especialidad-container">
              <span class="especialidad">{{ getEspecialidadNombre(cita.especialidad) }}</span>
            </div>
          </div>
          <div class="cita-detalles">
            <div class="fecha-hora">
              <i class="fas fa-calendar-alt"></i>
              {{ obtenerFechaCita(cita) }}
            </div>
            <div class="motivo" v-if="cita.motivo">
              <i class="fas fa-notes-medical"></i>
              {{ cita.motivo }}
            </div>
          </div>
        </div>
        <div class="cita-acciones">
          <button @click="confirmarCancelacion(cita)" class="btn-cancelar">
            <i class="fas fa-times"></i>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Props para recibir la función de volver desde el componente padre
const emit = defineEmits(['volver'])

const loading = ref(true)
const citas = ref([])

onMounted(async () => {
  await cargarCitas()
})

const cargarCitas = async () => {
  try {
    const email = localStorage.getItem('usuarioEmail')
    if (!email) {
      alert('No se encontró información de usuario. Por favor, inicia sesión nuevamente.')
      emit('volver')
      return
    }

    const response = await axios.get(`http://localhost:8081/api/paciente/citas?email=${encodeURIComponent(email)}`)

    console.log('=== DEBUG CITAS ===')
    console.log('Total citas recibidas:', response.data.length)
    console.log('Todas las citas:', response.data)

    // Mostrar estructura detallada de cada cita
    response.data.forEach((cita, index) => {
      console.log(`Cita ${index + 1}:`, cita)
      console.log(`Campos de fecha disponibles:`, {
        fechaHoraInicio: cita.fechaHoraInicio,
        fechaAgendada: cita.fechaAgendada,
        fecha: cita.fecha,
        fechaCita: cita.fechaCita,
        fechaHora: cita.fechaHora,
        // Buscar otros campos que contengan 'fecha'
        ...Object.keys(cita).filter(key => key.toLowerCase().includes('fecha')).reduce((obj, key) => {
          obj[key] = cita[key]
          return obj
        }, {})
      })
    })

    // Filtrar solo las citas que se pueden cancelar (AGENDADA)
    citas.value = response.data.filter(cita => cita.estado === 'AGENDADA')
    console.log('Citas filtradas (AGENDADA):', citas.value.length)

  } catch (error) {
    console.error('Error al cargar citas:', error)
    alert('Error al cargar las citas')
  } finally {
    loading.value = false
  }
}

// Nueva función mejorada para obtener la fecha
const obtenerFechaCita = (cita) => {
  console.log('Obteniendo fecha para cita:', cita.id)

  // Lista de posibles campos de fecha en orden de prioridad
  const camposFecha = [
    'fechaHoraInicio',
    'fechaAgendada',
    'fecha',
    'fechaCita',
    'fechaHora',
    'dateTime',
    'appointmentDate',
    'scheduledDate'
  ]

  for (const campo of camposFecha) {
    if (cita[campo]) {
      console.log(`Usando campo '${campo}' con valor:`, cita[campo])
      const fechaFormateada = formatearFecha(cita[campo])
      console.log(`Fecha formateada:`, fechaFormateada)
      return fechaFormateada
    }
  }

  // Si no encuentra ningún campo, mostrar todos los campos disponibles
  console.warn('No se encontró fecha en ningún campo esperado. Campos disponibles:', Object.keys(cita))

  // Buscar cualquier campo que contenga 'fecha' o 'date'
  const campoConFecha = Object.keys(cita).find(key =>
    key.toLowerCase().includes('fecha') || key.toLowerCase().includes('date')
  )

  if (campoConFecha && cita[campoConFecha]) {
    console.log(`Usando campo encontrado '${campoConFecha}':`, cita[campoConFecha])
    return formatearFecha(cita[campoConFecha])
  }

  return 'Fecha no disponible'
}

const getIconoEspecialidad = (especialidad) => {
  const iconos = {
    'CARDIOLOGIA': 'fas fa-heartbeat',
    'DERMATOLOGIA': 'fas fa-user-md',
    'NEUROLOGIA': 'fas fa-brain',
    'PEDIATRIA': 'fas fa-baby',
    'MEDICINA_GENERAL': 'fas fa-stethoscope',
    'GINECOLOGIA': 'fas fa-female',
    'TRAUMATOLOGIA': 'fas fa-bone'
  }
  return iconos[especialidad] || 'fas fa-user-md'
}

const getEspecialidadNombre = (especialidad) => {
  const especialidades = {
    'CARDIOLOGIA': 'Cardiología',
    'DERMATOLOGIA': 'Dermatología',
    'NEUROLOGIA': 'Neurología',
    'PEDIATRIA': 'Pediatría',
    'MEDICINA_GENERAL': 'Medicina General',
    'GINECOLOGIA': 'Ginecología',
    'TRAUMATOLOGIA': 'Traumatología'
  }
  return especialidades[especialidad] || especialidad
}

const formatearFecha = (fecha) => {
  if (!fecha) {
    console.log('formatearFecha: fecha es null/undefined')
    return ''
  }

  console.log('formatearFecha input:', fecha, 'tipo:', typeof fecha)

  try {
    const fechaObj = new Date(fecha)

    // Verificar si la fecha es válida
    if (isNaN(fechaObj.getTime())) {
      console.error('formatearFecha: fecha inválida:', fecha)
      return 'Fecha inválida'
    }

    const fechaFormateada = fechaObj.toLocaleString('es-CL', {
      weekday: 'long',
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    })

    console.log('formatearFecha output:', fechaFormateada)
    return fechaFormateada
  } catch (error) {
    console.error('Error al formatear fecha:', error, 'Input:', fecha)
    return 'Error en fecha'
  }
}

const confirmarCancelacion = (cita) => {
  const nombreMedico = cita.nombreMedico || cita.medico?.nombre || 'Médico no especificado'
  const fechaCita = obtenerFechaCita(cita)

  const confirmacion = confirm(`¿Estás seguro de que deseas cancelar la cita con Dr. ${nombreMedico}${fechaCita !== 'Fecha no disponible' ? ` el ${fechaCita}` : ''}?`)

  if (confirmacion) {
    cancelarCita(cita.id)
  }
}

const cancelarCita = async (citaId) => {
  try {
    const email = localStorage.getItem('usuarioEmail')
    if (!email) {
      alert('No se encontró información de usuario.')
      return
    }

    await axios.delete(`http://localhost:8081/api/paciente/citas/${citaId}?email=${encodeURIComponent(email)}`)

    // Mostrar mensaje de éxito y volver al inicio
    alert('Cita cancelada exitosamente')
    emit('volver')

  } catch (error) {
    console.error('Error al cancelar cita:', error)
    const errorMsg = error.response?.data?.error ||
                    error.response?.data ||
                    error.message ||
                    'Error desconocido'
    alert('Error al cancelar la cita:\n' + errorMsg)
  }
}
</script>

<style scoped>
.cancelar-cita-container {
  padding: 2rem;
  max-width: 800px;
  margin: 0 auto;
  min-height: 100vh;
}

.header {
  margin-bottom: 2rem;
}

.cancelar-cita-container h2 {
  color: white;
  margin: 0;
  font-size: 2.5rem;
  font-weight: 300;
  letter-spacing: 2px;
  text-transform: uppercase;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  margin-bottom: 0.5rem;
}

.subtitle {
  color: rgba(255, 255, 255, 0.9);
  margin: 0;
  font-size: 1.1rem;
  font-weight: 300;
  letter-spacing: 0.5px;
}

.loading {
  text-align: center;
  padding: 3rem;
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.8);
  background: #4b5563;
  border-radius: 10px;
}

.no-citas {
  text-align: center;
  padding: 3rem;
  color: rgba(255, 255, 255, 0.8);
  background: #4b5563;
  border-radius: 15px;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.no-citas-icon {
  font-size: 4rem;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 1rem;
}

.no-citas h3 {
  color: white;
  margin: 1rem 0;
  font-size: 1.5rem;
  font-weight: 300;
}

.no-citas p {
  font-size: 1.1rem;
  margin-bottom: 2rem;
  opacity: 0.9;
}

.citas-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.cita-card {
  background: #4b5563;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 15px;
  padding: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.cita-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
  border-color: rgba(255, 255, 255, 0.2);
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
  font-size: 1.2rem;
  font-weight: 500;
}

.especialidad-container {
  margin-top: 0.5rem;
}

.especialidad {
  background: #6b7280;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 25px;
  font-size: 0.9rem;
  font-weight: 400;
  display: inline-block;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.cita-detalles {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.fecha-hora,
.motivo {
  color: rgba(255, 255, 255, 0.85);
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.fecha-hora i,
.motivo i {
  color: rgba(255, 255, 255, 0.7);
  width: 16px;
}

.cita-acciones {
  flex-shrink: 0;
  margin-left: 1rem;
}

.btn-cancelar {
  background: #A63D40;
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

.btn-cancelar:hover {
  background: #8A3235;
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(166, 61, 64, 0.4);
}

.btn-cancelar:active {
  transform: translateY(0);
}

/* Responsive design */
@media (max-width: 768px) {
  .cancelar-cita-container {
    padding: 1rem;
  }

  .cancelar-cita-container h2 {
    font-size: 2rem;
  }

  .cita-card {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
    padding: 1rem;
  }

  .cita-acciones {
    margin-left: 0;
  }

  .btn-cancelar {
    width: 100%;
    justify-content: center;
  }

  .cita-detalles {
    gap: 0.75rem;
  }
}
</style>
