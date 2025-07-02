<template>
  <div class="agendar-cita">
    <div class="header">
      <button @click="volver" class="btn-volver">← Volver</button>
      <h1>Confirmar Cita Médica</h1>
    </div>

    <div class="cita-resumen">
      <h2>Resumen de la cita</h2>
      <div class="resumen-item">
        <strong>Especialidad:</strong> {{ especialidadNombre }}
      </div>
      <div class="resumen-item">
        <strong>Médico:</strong> Dr. {{ nombreMedico }}
      </div>
      <div class="resumen-item">
        <strong>Fecha y hora:</strong> {{ fechaHoraFormateada }}
      </div>
    </div>

    <div class="formulario">
      <div class="campo">
        <label for="motivo">Motivo de la consulta:</label>
        <textarea
          id="motivo"
          v-model="motivo"
          placeholder="Describe brevemente el motivo de tu consulta..."
          rows="4"
          required
        ></textarea>
      </div>

      <div class="acciones">
        <button @click="volver" class="btn-cancelar">Cancelar</button>
        <button @click="agendarCita" class="btn-confirmar" :disabled="!motivo.trim()">
          Confirmar Cita
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const medicoId = ref(route.params.id || route.query.medicoId || '')
const fechaHora = ref(route.query.fechaHoraInicio || '')
const motivo = ref(route.query.motivo || '')
const especialidad = ref(route.query.especialidad || '')
const nombreMedico = ref(route.query.nombre || '')

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

const fechaHoraFormateada = computed(() => {
  if (!fechaHora.value) return ''
  return new Date(fechaHora.value).toLocaleString('es-CL', {
    weekday: 'long',
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
})

onMounted(() => {
  // Validar que todos los datos necesarios estén presentes
  if (!medicoId.value || !fechaHora.value || !especialidad.value) {
    alert('Faltan datos para agendar la cita. Serás redirigido.')
    router.push('/paciente/especialidades')
  }
})

const agendarCita = async () => {
  try {
    if (!motivo.value.trim()) {
      alert('Por favor ingrese el motivo de la consulta')
      return
    }

    const email = localStorage.getItem('usuarioEmail')
    if (!email) {
      alert('No se encontró información de usuario. Por favor, inicia sesión nuevamente.')
      router.push('/')
      return
    }

    const response = await axios.post(
      `http://localhost:8081/api/paciente/citas?email=${encodeURIComponent(email)}`,
      {
        medicoId: Number(medicoId.value),
        fechaHoraInicio: fechaHora.value,
        motivo: motivo.value.trim(),
        especialidad: especialidad.value
      }
    )

    alert('¡Cita agendada exitosamente!')
    router.push('/paciente/ver-citas')
  } catch (error) {
    console.error('Error al agendar cita:', error)
    const errorMsg = error.response?.data?.error ||
                    error.response?.data ||
                    error.message ||
                    'Error desconocido'
    alert('Error al agendar la cita:\n' + errorMsg)
  }
}

const volver = () => {
  router.go(-1) // Volver a la página anterior
}
</script>

<style scoped>
.agendar-cita {
  max-width: 600px;
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

.agendar-cita h1 {
  color: #2c3e50;
  text-align: center;
  margin: 0;
}

.cita-resumen {
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 2rem;
}

.cita-resumen h2 {
  color: #2c3e50;
  margin-top: 0;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.resumen-item {
  margin-bottom: 0.75rem;
  padding: 0.5rem 0;
  border-bottom: 1px solid #e9ecef;
}

.resumen-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

.resumen-item strong {
  color: #495057;
  display: inline-block;
  width: 120px;
}

.formulario {
  background: white;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 1.5rem;
}

.campo {
  margin-bottom: 1.5rem;
}

.campo label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 600;
  color: #495057;
}

.campo textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 6px;
  font-family: inherit;
  font-size: 1rem;
  resize: vertical;
  min-height: 100px;
}

.campo textarea:focus {
  outline: none;
  border-color: #3498db;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

.acciones {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}

.btn-cancelar {
  background: #6c757d;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
}

.btn-cancelar:hover {
  background: #545b62;
}

.btn-confirmar {
  background: #28a745;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s ease;
}

.btn-confirmar:hover:not(:disabled) {
  background: #218838;
}

.btn-confirmar:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .agendar-cita {
    margin: 1rem;
    padding: 0;
  }

  .acciones {
    flex-direction: column;
  }

  .btn-cancelar,
  .btn-confirmar {
    width: 100%;
  }

  .resumen-item strong {
    width: 100px;
    font-size: 0.9rem;
  }
}
</style>