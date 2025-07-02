<template>
  <div class="mis-citas-container">
    <div class="section-header">
      <h1>Mis Citas Agendadas</h1>
    </div>

    <div class="content-wrapper">
      <div v-if="loading" class="loading-container">
        <div class="loading-spinner"></div>
        <p>Cargando tus citas...</p>
      </div>

      <div v-else-if="error" class="error-container">
        <div class="error-icon">⚠️</div>
        <h3>Error al cargar las citas</h3>
        <p>{{ error }}</p>
        <button @click="fetchCitas" class="btn-retry">Reintentar</button>
      </div>

      <div v-else-if="citas.length === 0" class="empty-container">
        <div class="empty-icon">📅</div>
        <h3>No hay citas programadas</h3>
        <p>Actualmente no tienes citas médicas agendadas.</p>
        <button @click="navigateToAgendar" class="btn-agendar">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
            <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
          </svg>
          Agendar Nueva Cita
        </button>
      </div>

      <div v-else class="citas-content">
        <div class="table-container">
          <table class="citas-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Médico</th>
                <th>Especialidad</th>
                <th>Fecha & Hora</th>
                <th>Motivo</th>
                <th>Estado</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="cita in citas" :key="cita.id" class="cita-row">
                <td class="cita-id">#{{ cita.id }}</td>
                <td class="cita-medico">
                  <div class="medico-info">
                    <div class="medico-avatar">
                      {{ getInitials(cita.medico.nombre) }}
                    </div>
                    <div class="medico-details">
                      <strong>{{ cita.medico.nombre }}</strong>
                      <small>{{ cita.medico.email }}</small>
                    </div>
                  </div>
                </td>
                <td class="cita-especialidad">
                  <span class="especialidad-badge">
                    {{ cita.medico.especialidad || 'Medicina General' }}
                  </span>
                </td>
                <td class="cita-fecha">
                  <div class="fecha-info">
                    <strong>{{ formatDate(cita.fechaHora) }}</strong>
                    <small>{{ formatTime(cita.fechaHora) }}</small>
                  </div>
                </td>
                <td class="cita-motivo">{{ cita.motivo }}</td>
                <td class="cita-estado">
                  <span :class="getEstadoClass(cita.estado)">
                    {{ cita.estado }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const citas = ref([])
const loading = ref(true)
const error = ref(null)

// Funciones de utilidad
function getInitials(nombre) {
  return nombre.split(' ').map(n => n[0]).join('').toUpperCase()
}

function formatDate(dt) {
  return new Date(dt).toLocaleDateString('es-ES', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  })
}

function formatTime(dt) {
  return new Date(dt).toLocaleTimeString('es-ES', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

function getEstadoClass(estado) {
  const baseClass = 'estado-badge'
  switch (estado.toLowerCase()) {
    case 'programada':
      return `${baseClass} estado-programada`
    case 'completada':
      return `${baseClass} estado-completada`
    case 'cancelada':
      return `${baseClass} estado-cancelada`
    case 'reprogramada':
      return `${baseClass} estado-reprogramada`
    default:
      return baseClass
  }
}

// Funciones principales
async function fetchCitas() {
  loading.value = true
  error.value = null

  try {
    const email = localStorage.getItem('usuarioEmail')
    if (!email) throw new Error('No estás autenticado')

    const resp = await axios.get(
      `http://localhost:8081/api/paciente/citas?email=${encodeURIComponent(email)}`
    )
    citas.value = resp.data
  } catch (e) {
    console.error(e)
    error.value = e.response?.data?.error || e.message
  } finally {
    loading.value = false
  }
}

const navigateToAgendar = () => {
  router.push({ name: 'ElegirEspecialidad' })
}

onMounted(fetchCitas)
</script>

<style scoped>
.mis-citas-container {
  padding: 2rem;
  min-height: 100vh;
  color: white;
  position: relative;
  z-index: 10;
}

.section-header {
  text-align: center;
  margin-bottom: 3rem;
}

.section-header h1 {
  color: white;
  font-size: 3rem;
  margin-bottom: 0.5rem;
  font-weight: 300;
  letter-spacing: 2px;
  text-transform: uppercase;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.content-wrapper {
  position: relative;
  z-index: 15;
}

/* Loading Styles */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  text-align: center;
}

.loading-spinner {
  width: 50px;
  height: 50px;
  border: 3px solid rgba(255, 255, 255, 0.3);
  border-top: 3px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* Error Styles */
.error-container {
  background: rgba(231, 76, 60, 0.15);
  border: 1px solid rgba(231, 76, 60, 0.3);
  border-radius: 16px;
  padding: 3rem;
  text-align: center;
  backdrop-filter: blur(10px);
}

.error-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.error-container h3 {
  color: #ff6b6b;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.btn-retry {
  background: rgba(231, 76, 60, 0.2);
  color: #ff6b6b;
  border: 1px solid rgba(231, 76, 60, 0.5);
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  margin-top: 1rem;
  transition: all 0.3s ease;
}

.btn-retry:hover {
  background: rgba(231, 76, 60, 0.3);
}

/* Empty State */
.empty-container {
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  padding: 4rem;
  text-align: center;
  backdrop-filter: blur(15px);
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1.5rem;
}

.empty-container h3 {
  font-size: 1.8rem;
  margin-bottom: 1rem;
  font-weight: 300;
}

.btn-agendar {
  background: rgba(32, 178, 170, 0.2);
  color: #20b2aa;
  border: 1px solid rgba(32, 178, 170, 0.5);
  padding: 1rem 2rem;
  border-radius: 12px;
  cursor: pointer;
  margin-top: 2rem;
  font-size: 1rem;
  font-weight: 500;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
}

.btn-agendar:hover {
  background: rgba(32, 178, 170, 0.3);
  transform: translateY(-2px);
}

/* Citas Content - Tonalidad Gris */
.citas-content {
  width: 100%;
}

.table-container {
  background: linear-gradient(135deg, #4b5563 0%, #6b7280 100%);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 24px;
  overflow: hidden;
  backdrop-filter: blur(20px);
  box-shadow:
    0 25px 50px rgba(0, 0, 0, 0.25),
    0 0 0 1px rgba(255, 255, 255, 0.1),
    inset 0 1px 0 rgba(255, 255, 255, 0.1);
}

.citas-table {
  width: 100%;
  border-collapse: collapse;
}

.citas-table th {
  background: linear-gradient(135deg, #374151 0%, #4b5563 100%);
  color: #ffffff;
  padding: 1.8rem 1.5rem;
  text-align: left;
  font-weight: 700;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 1.2px;
  border-bottom: 2px solid rgba(255, 255, 255, 0.1);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  position: relative;
}

.citas-table th::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
}

.citas-table td {
  padding: 2rem 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  color: #ffffff;
  font-size: 0.95rem;
}

.cita-row {
  background: rgba(255, 255, 255, 0.02);
  transition: all 0.3s ease;
  position: relative;
}

.cita-row:nth-child(even) {
  background: rgba(255, 255, 255, 0.04);
}

.cita-row:hover {
  background: rgba(255, 255, 255, 0.1);
  /* Eliminado el efecto de deslizado */
  /* transform: translateX(5px); */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* Eliminado el efecto de línea lateral en hover */
/* .cita-row:hover::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 4px;
  background: linear-gradient(180deg, #20b2aa, #3282b8);
  border-radius: 0 2px 2px 0;
} */

.cita-id {
  font-weight: 700;
  color: #9ca3af;
  font-size: 1.1rem;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.medico-info {
  display: flex;
  align-items: center;
  gap: 1.2rem;
}

.medico-avatar {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #6b7280 0%, #4b5563 100%);
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 1rem;
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  box-shadow:
    0 4px 12px rgba(0, 0, 0, 0.2),
    inset 0 1px 0 rgba(255, 255, 255, 0.2);
}

.medico-details strong {
  display: block;
  margin-bottom: 0.4rem;
  font-size: 1.05rem;
  font-weight: 600;
  color: #ffffff;
}

.medico-details small {
  color: rgba(255, 255, 255, 0.75);
  font-size: 0.85rem;
  font-style: italic;
}

.especialidad-badge {
  background: linear-gradient(135deg, rgba(107, 114, 128, 0.3) 0%, rgba(75, 85, 99, 0.3) 100%);
  color: #ffffff;
  padding: 0.6rem 1.2rem;
  border-radius: 25px;
  font-size: 0.9rem;
  font-weight: 600;
  border: 1px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(5px);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.fecha-info {
  text-align: left;
}

.fecha-info strong {
  display: block;
  margin-bottom: 0.4rem;
  font-size: 1rem;
  font-weight: 600;
  color: #ffffff;
}

.fecha-info small {
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.9rem;
  font-weight: 500;
}

.cita-motivo {
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.95rem;
  line-height: 1.4;
}

.estado-badge {
  padding: 0.6rem 1.2rem;
  border-radius: 25px;
  font-size: 0.85rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.estado-programada {
  background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
  color: #ffffff;
}

.estado-completada {
  background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
  color: #ffffff;
}

.estado-cancelada {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
  color: #ffffff;
}

.estado-reprogramada {
  background: linear-gradient(135deg, #f39c12 0%, #e67e22 100%);
  color: #ffffff;
}

/* Responsive */
@media (max-width: 768px) {
  .mis-citas-container {
    padding: 1rem;
  }

  .section-header h1 {
    font-size: 2rem;
  }

  .table-container {
    overflow-x: auto;
    border-radius: 16px;
  }

  .citas-table {
    min-width: 700px;
  }

  .citas-table th,
  .citas-table td {
    padding: 1.2rem 0.8rem;
  }

  .citas-table th {
    font-size: 0.85rem;
    padding: 1.5rem 0.8rem;
  }

  .medico-info {
    gap: 0.8rem;
  }

  .medico-avatar {
    width: 40px;
    height: 40px;
    font-size: 0.9rem;
  }

  .medico-details strong {
    font-size: 1rem;
  }

  .especialidad-badge {
    padding: 0.5rem 1rem;
    font-size: 0.8rem;
  }

  .estado-badge {
    padding: 0.5rem 1rem;
    font-size: 0.75rem;
  }
}

@media (max-width: 480px) {
  .citas-table th,
  .citas-table td {
    padding: 1rem 0.5rem;
  }

  .medico-avatar {
    width: 36px;
    height: 36px;
    font-size: 0.8rem;
  }
}
</style>x
