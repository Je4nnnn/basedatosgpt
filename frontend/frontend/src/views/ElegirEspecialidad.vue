<template>
  <div class="elegir-especialidad-container">
    <div class="section-header">
      <h2>Seleccionar Especialidad</h2>
      <p>Elige la especialidad médica que necesitas</p>
    </div>

    <div v-if="loading" class="loading">
      Cargando especialidades...
    </div>

    <div v-else class="especialidades-grid">
      <div
        v-for="especialidad in especialidades"
        :key="especialidad.codigo"
        class="especialidad-card"
        @click="seleccionarEspecialidad(especialidad)"
      >
        <div class="especialidad-icon">
          <i :class="getIconoEspecialidad(especialidad.codigo)"></i>
        </div>
        <h3>{{ especialidad.nombre }}</h3>
        <p>{{ especialidad.descripcion }}</p>
        <div class="especialidad-arrow">→</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

// Restaurar el router
const router = useRouter()

const loading = ref(true)

const especialidades = ref([
  {
    codigo: 'CARDIOLOGIA',
    nombre: 'Cardiología',
    descripcion: 'Especialista en enfermedades del corazón y sistema cardiovascular'
  },
  {
    codigo: 'DERMATOLOGIA',
    nombre: 'Dermatología',
    descripcion: 'Especialista en enfermedades de la piel, cabello y uñas'
  },
  {
    codigo: 'NEUROLOGIA',
    nombre: 'Neurología',
    descripcion: 'Especialista en enfermedades del sistema nervioso'
  },
  {
    codigo: 'PEDIATRIA',
    nombre: 'Pediatría',
    descripcion: 'Especialista en atención médica de niños y adolescentes'
  },
  {
    codigo: 'MEDICINA_GENERAL',
    nombre: 'Medicina General',
    descripcion: 'Atención médica integral y consultas generales'
  },
  {
    codigo: 'GINECOLOGIA',
    nombre: 'Ginecología',
    descripcion: 'Especialista en salud reproductiva femenina'
  },
  {
    codigo: 'TRAUMATOLOGIA',
    nombre: 'Traumatología',
    descripcion: 'Especialista en lesiones del sistema músculo-esquelético'
  }
])

onMounted(() => {
  setTimeout(() => {
    loading.value = false
  }, 500)
})

const getIconoEspecialidad = (codigo) => {
  const iconos = {
    'CARDIOLOGIA': 'fas fa-heartbeat',
    'DERMATOLOGIA': 'fas fa-user-md',
    'NEUROLOGIA': 'fas fa-brain',
    'PEDIATRIA': 'fas fa-baby',
    'MEDICINA_GENERAL': 'fas fa-stethoscope',
    'GINECOLOGIA': 'fas fa-female',
    'TRAUMATOLOGIA': 'fas fa-bone'
  }
  return iconos[codigo] || 'fas fa-user-md'
}

// Restaurar la navegación directa
const seleccionarEspecialidad = (especialidad) => {
  router.push({
    path: '/paciente/medicos-especialidad',
    query: { especialidad: especialidad.codigo }
  })
}
</script>

<style scoped>
.elegir-especialidad-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
}

.section-header {
  text-align: center;
  margin-bottom: 3rem;
}

.section-header h2 {
  color: white;
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
  font-weight: 700;
}

.section-header p {
  color: rgba(255, 255, 255, 0.8);
  font-size: 1.2rem;
  font-weight: 300;
}

.loading {
  text-align: center;
  padding: 3rem;
  font-size: 1.1rem;
  color: rgba(255, 255, 255, 0.8);
}

.especialidades-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 2rem;
}

.especialidad-card {
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 20px;
  padding: 2.5rem 2rem;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.especialidad-card:hover {
  background: rgba(255, 255, 255, 0.25);
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
}

.especialidad-icon {
  font-size: 3rem;
  color: white;
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: center;
}

.especialidad-card h3 {
  color: white;
  margin: 0 0 1rem 0;
  font-size: 1.4rem;
  font-weight: 600;
}

.especialidad-card p {
  color: rgba(255, 255, 255, 0.9);
  margin: 0;
  line-height: 1.6;
  font-size: 1rem;
}

.especialidad-arrow {
  position: absolute;
  bottom: 1rem;
  right: 1.5rem;
  font-size: 1.5rem;
  color: white;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.especialidad-card:hover .especialidad-arrow {
  opacity: 1;
}

/* Responsive design */
@media (max-width: 768px) {
  .elegir-especialidad-container {
    padding: 0 1rem;
  }

  .section-header h2 {
    font-size: 2rem;
  }

  .section-header p {
    font-size: 1rem;
  }

  .especialidades-grid {
    grid-template-columns: 1fr;
    gap: 1.5rem;
  }

  .especialidad-card {
    padding: 2rem 1.5rem;
  }

  .especialidad-card h3 {
    font-size: 1.2rem;
  }

  .especialidad-card p {
    font-size: 0.9rem;
  }
}
</style>