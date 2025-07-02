<template>
  <div class="dashboard-container">
    <!-- Sidebar -->
    <div class="sidebar">
      <div class="sidebar-header">
        <div class="logo">
          <div class="logo-icon">+</div>
          <span>Medicita</span>
        </div>
      </div>

      <nav class="sidebar-nav">
        <div class="nav-item" :class="{ active: activeSection === 'Inicio' }" @click="setActiveSection('Inicio')" :title="'Inicio'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"/>
            </svg>
          </div>
          <span class="nav-label">Inicio</span>
        </div>

        <div class="nav-item" :class="{ active: activeSection === 'Agendar' }" @click="setActiveSection('Agendar')" :title="'Agendar Cita'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM7 10h5v5H7z"/>
            </svg>
          </div>
          <span class="nav-label">Agendar</span>
        </div>

        <div class="nav-item" :class="{ active: activeSection === 'VerCitas' }" @click="setActiveSection('VerCitas')" :title="'Ver Citas'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"/>
            </svg>
          </div>
          <span class="nav-label">Ver Citas</span>
        </div>

        <div class="nav-item" :class="{ active: activeSection === 'Cancelar' }" @click="navigateToSection('Cancelar')" :title="'Cancelar Cita'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/>
            </svg>
          </div>
          <span class="nav-label">Cancelar Cita</span>
        </div>

        <div class="nav-item" :class="{ active: activeSection === 'Historial' }" @click="navigateToSection('Historial')" :title="'Historial Médico'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M12 1L3 5v6c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V5l-9-4z"/>
            </svg>
          </div>
          <span class="nav-label">Historial</span>
        </div>

        <div class="nav-item" :class="{ active: activeSection === 'Reprogramar' }" @click="navigateToSection('Reprogramar')" :title="'Reprogramar Cita'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M19 8l-4 4h3c0 3.31-2.69 6-6 6s-6-2.69-6-6c0-3.31 2.69-6 6-6v4l4-4-4-4v4c-4.42 0-8 3.58-8 8s3.58 8 8 8 8-3.58 8-8h-3z"/>
            </svg>
          </div>
          <span class="nav-label">Reprogramar</span>
        </div>

        <div class="nav-item" @click="logout" :title="'Salir'">
          <div class="nav-icon">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="currentColor">
              <path d="M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.59L17 17l5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z"/>
            </svg>
          </div>
          <span class="nav-label">Salir</span>
        </div>
      </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
      <!-- Fondo médico solo para la sección Inicio -->
      <div v-if="activeSection === 'Inicio'" class="medical-background">
        <!-- Capa de imagen de fondo -->
        <div class="medical-background-layer"></div>

        <!-- Formas flotantes solo en Inicio -->
        <div class="floating-shapes">
          <div class="shape shape-1"></div>
          <div class="shape shape-2"></div>
          <div class="shape shape-3"></div>
          <div class="shape shape-4"></div>
          <div class="shape shape-5"></div>
        </div>

        <!-- Overlay con gradiente -->
        <div class="gradient-overlay"></div>
      </div>

      <!-- Inicio Section -->
      <div v-if="activeSection === 'Inicio'" class="section inicio-section">
        <!-- Card transparente con título -->
        <div class="transparent-card">
          <div class="card-content">
            <div class="title-section">
              <h2 class="main-title">TU PLATAFORMA<br>MÉDICA<br>INTEGRAL</h2>
              <p class="description-text">
                Accede a una atención médica de calidad con tecnología de vanguardia.
                Agenda citas, consulta tu historial médico y gestiona tu salud de manera
                integral en un solo lugar.
              </p>
            </div>
          </div>
        </div>
      </div>

      <!-- Agendar Section -->
      <div v-if="activeSection === 'Agendar'" class="section">
        <ElegirEspecialidad @volver="setActiveSection('Inicio')" />
      </div>

      <!-- Ver Citas Section - Ahora usando el componente integrado -->
      <div v-if="activeSection === 'VerCitas'" class="section ver-citas-section">
        <VerMisCitas @volver="setActiveSection('Inicio')" />
      </div>

      <!-- Cancelar Cita Section - NUEVA SECCIÓN -->
      <div v-if="activeSection === 'Cancelar'" class="section cancelar-cita-section">
        <CancelarCita @volver="setActiveSection('Inicio')" />
      </div>

    <!-- Reprogramar Cita Section - NUEVA SECCIÓN -->
    <div v-if="activeSection === 'Reprogramar'" class="section reprogramar-cita-section">
      <ReprogramarCita
        @volver="setActiveSection('Inicio')"
        @navegarAEspecialidad="handleNavegacionEspecialidad"
      />
    </div>

    <!-- Otras secciones -->
    <div v-if="!['Inicio', 'Agendar', 'VerCitas', 'Cancelar', 'Reprogramar'].includes(activeSection)" class="section">
        <div class="section-header">
          <h2>{{ getSectionTitle() }}</h2>
          <p>{{ getSectionDescription() }}</p>
        </div>
        <div class="section-content">
          <div class="content-card">
            <p>{{ getSectionContent() }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ElegirEspecialidad from './ElegirEspecialidad.vue'
import VerMisCitas from './VerMisCitas.vue'
import CancelarCita from './CancelarCita.vue'
import ReprogramarCita from './ReprogramarCita.vue'  // <-- Agregar este import

const router = useRouter()
const activeSection = ref('Inicio')

const setActiveSection = (section) => {
  activeSection.value = section
}

// Función para manejar la navegación
const navigateToSection = (section) => {
  // Mapeo de secciones a nombres de rutas
  const routeMap = {
    'Cancelar': 'CancelarCita',
    'Historial': 'HistorialMedico',
    'Reprogramar': 'ReprogramarCita'
  }

  // Para las secciones que se manejan internamente
  if (['Agendar', 'VerCitas', 'Cancelar', 'Reprogramar'].includes(section)) {
    setActiveSection(section)
    return
  }

  // Para las demás secciones, navegamos a la ruta correspondiente
  const routeName = routeMap[section]
  if (routeName) {
    router.push({ name: routeName })
  }
}

// AGREGAR AQUÍ LA NUEVA FUNCIÓN:
// Función para manejar la navegación desde ReprogramarCita hacia ElegirEspecialidad
const handleNavegacionEspecialidad = (data) => {
  // Cambiar a la sección Agendar y pasar los datos
  setActiveSection('Agendar')
  // Aquí puedes pasar los datos del contexto de reprogramación si es necesario
  // Por ejemplo, usando un store o emitiendo eventos
}

const logout = () => {
  // Limpiar datos de sesión si es necesario
  localStorage.removeItem('usuarioEmail')
  router.push({ name: 'InicioSesion' })
}

const getSectionTitle = () => {
  const titles = {
    'Cancelar': 'Cancelar Cita',
    'Historial': 'Historial Médico',
    'Reprogramar': 'Reprogramar Cita'
  }
  return titles[activeSection.value] || 'Sección'
}

const getSectionDescription = () => {
  const descriptions = {
    'Cancelar': 'Gestiona la cancelación de tus citas',
    'Historial': 'Tu información médica completa',
    'Reprogramar': 'Modifica tus citas existentes'
  }
  return descriptions[activeSection.value] || 'Descripción'
}

const getSectionContent = () => {
  const contents = {
    'Cancelar': 'Selecciona la cita que deseas cancelar y confirma la acción.',
    'Historial': 'Accede a tu historial médico, diagnósticos, tratamientos y evolución de tu salud.',
    'Reprogramar': 'Cambia la fecha u hora de tus citas programadas según tu disponibilidad.'
  }
  return contents[activeSection.value] || 'Contenido de la sección'
}
</script>

<style scoped>
/* Reset global para eliminar espacios */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow-x: hidden;
}

.dashboard-container {
  display: flex;
  min-height: 100vh;
  height: 100vh;
  background: #1a1a2e;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  margin: 0;
  padding: 0;
  width: 100vw;
  overflow: hidden;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

/* Sidebar Styles */
.sidebar {
  width: 80px;
  background: #4B5563;
  color: white;
  overflow-y: auto;
  position: fixed;
  height: 100vh;
  left: 0;
  top: 0;
  z-index: 1000;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.sidebar::-webkit-scrollbar {
  display: none;
}

.sidebar-header {
  padding: 1.5rem 0;
  text-align: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.logo-icon {
  width: 32px;
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  font-weight: bold;
  color: white;
}

.logo span {
  font-size: 0.75rem;
  font-weight: 600;
  color: white;
}

.sidebar-nav {
  padding: 2rem 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
}

.nav-item {
  width: 60px;
  height: 60px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 12px;
  position: relative;
  padding: 4px;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.15);
  transform: scale(1.05);
}

.nav-item.active {
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.nav-item.active::before {
  content: '';
  position: absolute;
  left: -12px;
  top: 50%;
  transform: translateY(-50%);
  width: 3px;
  height: 24px;
  background: white;
  border-radius: 0 2px 2px 0;
}

.nav-icon {
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-bottom: 2px;
}

.nav-label {
  font-size: 0.65rem;
  color: white;
  text-align: center;
  line-height: 1;
  opacity: 0.9;
  font-weight: 500;
}

/* Main Content */
.main-content {
  flex: 1;
  margin-left: 80px;
  padding: 0;
  overflow-x: hidden;
  overflow-y: auto;
  height: 100vh;
  width: calc(100vw - 80px);
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  /* Fondo sólido teal para todas las secciones excepto Inicio */
  background: #4A9B8F;
}

/* Fondo médico con imagen (solo para Inicio) */
.medical-background {
  position: fixed;
  top: 0;
  left: 80px;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 1;
}

/* Capa de imagen de fondo médica */
.medical-background-layer {
  position: absolute;
  top: -5%;
  left: -5%;
  right: -5%;
  bottom: -5%;
  background:
    linear-gradient(135deg,
      rgba(32, 178, 170, 0.8) 0%,
      rgba(72, 201, 176, 0.7) 25%,
      rgba(102, 224, 182, 0.6) 50%,
      rgba(32, 178, 170, 0.8) 75%,
      rgba(0, 139, 139, 0.9) 100%
    ),
    url('@/assets/medical-background.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-blend-mode: overlay;
  filter: blur(1px);
  opacity: 0.9;
  z-index: 1;
  animation: backgroundFloat 20s infinite ease-in-out alternate;
}

/* Formas flotantes solo para sección Inicio */
.floating-shapes {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden;
  z-index: 2;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: linear-gradient(135deg,
    rgba(32, 178, 170, 0.1),
    rgba(72, 201, 176, 0.05)
  );
  backdrop-filter: blur(20px);
  animation: float 25s infinite ease-in-out;
}

.shape-1 {
  width: 300px;
  height: 300px;
  top: -150px;
  left: -150px;
  animation-delay: 0s;
}

.shape-2 {
  width: 200px;
  height: 200px;
  top: 20%;
  right: -100px;
  animation-delay: -5s;
}

.shape-3 {
  width: 150px;
  height: 150px;
  bottom: 20%;
  left: 10%;
  animation-delay: -10s;
}

.shape-4 {
  width: 400px;
  height: 400px;
  bottom: -200px;
  right: -200px;
  animation-delay: -15s;
}

.shape-5 {
  width: 100px;
  height: 100px;
  top: 60%;
  right: 20%;
  animation-delay: -7s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
  }
  33% {
    transform: translateY(-30px) rotate(120deg);
  }
  66% {
    transform: translateY(15px) rotate(240deg);
  }
}

/* Animación para la imagen de fondo */
@keyframes backgroundFloat {
  0% {
    transform: scale(1) translateY(0);
    filter: blur(1px);
  }
  100% {
    transform: scale(1.05) translateY(-10px);
    filter: blur(2px);
  }
}

/* Overlay con gradiente para sección con imagen */
.gradient-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    135deg,
    rgba(32, 178, 170, 0.2) 0%,
    rgba(26, 26, 46, 0.4) 50%,
    rgba(32, 178, 170, 0.3) 100%
  );
  z-index: 3;
}

/* Section Styles */
.section {
  padding: 2rem;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  width: 100%;
  position: relative;
  z-index: 10;
}

/* Estilos específicos para la sección Ver Citas */
.ver-citas-section {
  padding: 0; /* Removemos el padding para que el componente VerMisCitas maneje su propio espaciado */
}
/* Estilos específicos para la sección Cancelar Cita */
.cancelar-cita-section {
  padding: 0; /* Removemos el padding para que el componente CancelarCita maneje su propio espaciado */
}
/* Estilos específicos para la sección Reprogramar Cita */
.reprogramar-cita-section {
  padding: 0; /* Removemos el padding para que el componente ReprogramarCita maneje su propio espaciado */
}

/* Inicio Section */
.inicio-section {
  justify-content: center;
  align-items: center;
  padding: 0;
}

/* Card transparente - En el lado izquierdo */
.transparent-card {
  position: absolute;
  top: 50%;
  left: 10%;
  transform: translateY(-50%);
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 3rem;
  max-width: 600px;
  width: 90%;
  z-index: 15;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.card-content {
  color: white;
}

.title-section {
  text-align: left;
}

.main-title {
  font-size: 3.5rem;
  font-weight: 300;
  line-height: 0.9;
  letter-spacing: 3px;
  margin-bottom: 2rem;
  text-transform: uppercase;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.description-text {
  font-size: 1rem;
  line-height: 1.7;
  color: rgba(255, 255, 255, 0.85);
  font-weight: 300;
  letter-spacing: 0.5px;
  max-width: 450px;
}

.section-header {
  text-align: center;
  margin-bottom: 3rem;
  position: relative;
  z-index: 15;
}

.section-header h2 {
  color: white;
  font-size: 3rem;
  margin-bottom: 0.5rem;
  font-weight: 300;
  letter-spacing: 2px;
  text-transform: uppercase;
  line-height: 1.2;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.section-header p {
  color: rgba(255, 255, 255, 0.95);
  font-size: 1.3rem;
  font-weight: 300;
  letter-spacing: 0.5px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.section-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 15;
}

.content-card {
  background: rgba(255, 255, 255, 0.12);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 20px;
  padding: 3rem;
  max-width: 800px;
  width: 100%;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
}

.content-card p {
  color: white;
  font-size: 1.1rem;
  text-align: center;
  line-height: 1.6;
  font-weight: 300;
  letter-spacing: 0.5px;
}

/* Responsive Design */
@media (max-width: 768px) {
  .dashboard-container {
    flex-direction: column;
    overflow-y: auto;
    position: relative;
  }

  .sidebar {
    width: 100%;
    position: relative;
    height: auto;
    background: #4B5563;
  }

  .sidebar-nav {
    flex-direction: row;
    justify-content: center;
    gap: 0.5rem;
    padding: 1rem 0;
    overflow-x: auto;
  }

  .nav-item {
    width: 50px;
    height: 50px;
    min-width: 50px;
  }

  .nav-label {
    font-size: 0.6rem;
  }

  .main-content {
    margin-left: 0;
    padding: 0;
    width: 100vw;
    height: auto;
    min-height: calc(100vh - 100px);
    position: relative;
  }

  .medical-background {
    left: 0;
  }

  .section {
    padding: 1.5rem;
    min-height: auto;
  }

  .ver-citas-section {
    padding: 0; /* Mantenemos sin padding en móvil también */
  }

  .transparent-card {
    position: relative;
    top: auto;
    left: auto;
    transform: none;
    margin: 2rem auto;
    padding: 2rem;
    max-width: 90%;
  }

  .content-card {
    padding: 2rem;
    max-width: 100%;
  }

  .main-title {
    font-size: 2.5rem;
    line-height: 1;
    margin-bottom: 1.5rem;
  }

  .description-text {
    font-size: 0.9rem;
    max-width: 100%;
  }

  .section-header h2 {
    font-size: 2.5rem;
  }

  .section-header p {
    font-size: 1.1rem;
  }

  .medical-background-layer {
    top: -10%;
    left: -10%;
    right: -10%;
    bottom: -10%;
  }

  .shape-1 {
    width: 200px;
    height: 200px;
  }

  .shape-2 {
    width: 150px;
    height: 150px;
  }

  .shape-3 {
    width: 100px;
    height: 100px;
  }

  .shape-4 {
    width: 250px;
    height: 250px;
  }

  .shape-5 {
    width: 80px;
    height: 80px;
  }
}
</style>