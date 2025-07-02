// frontend/src/router/index.js

import { createRouter, createWebHistory } from 'vue-router'

import Login              from '../views/Login.vue'
import RegistrarUsuario   from '../views/RegistrarUsuario.vue'
import Paciente           from '../views/Paciente.vue'
import Medico             from '../views/Medico.vue'
import VerDisponibilidad  from '../views/VerDisponibilidad.vue'
import AgendarCita        from '../views/AgendarCita.vue'
import VerCitasPaciente   from '../views/VerCitasPaciente.vue'
import VerMisCitas        from '../views/VerMisCitas.vue'
import HistorialMedico    from '../views/HistorialMedico.vue'
import CancelarCita       from '../views/CancelarCita.vue'
import ReprogramarCita    from '../views/ReprogramarCita.vue'
import RegistrarMedicamento   from '../views/RegistrarMedicamento.vue'
import ListarMedicamentos      from '../views/ListarMedicamentos.vue'
import Administrativo       from '../views/Administrativo.vue'
import UsuarioInventario    from '../views/UsuarioInventario.vue'
import RegistrarReceta from '../views/RegistrarReceta.vue'
import VerRecetas from '../views/VerRecetas.vue'
import VerStock from '../views/VerStock.vue'
import RegistrarStock from '../views/RegistrarStock.vue'


import ElegirEspecialidad from '../views/ElegirEspecialidad.vue'
import MedicosEspecialidad from '../views/MedicosEspecialidad.vue'


const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/registrar',
    name: 'RegistrarUsuario',
    component: RegistrarUsuario
  },
  {
    path: '/paciente',
    name: 'Paciente',
    component: Paciente
  },
  {
    path: '/medico',
    name: 'Medico',
    component: Medico
  },

  // FLUJO CORRECTO DE AGENDAMIENTO
  // 1. Elegir especialidad
  {
    path: '/paciente/especialidades',
    name: 'ElegirEspecialidad',
    component: ElegirEspecialidad
  },

  // 2. Ver médicos de la especialidad seleccionada
  {
    path: '/paciente/medicos-especialidad',
    name: 'MedicosEspecialidad',
    component: MedicosEspecialidad
  },

  // 3. Ver disponibilidad del médico seleccionado
  {
    path: '/paciente/disponibilidad/:id',
    name: 'VerDisponibilidad',
    component: VerDisponibilidad,
    props: true
  },

  // 4. Confirmar agendamiento
  {
    path: '/paciente/agendar/:id',
    name: 'AgendarCita',
    component: AgendarCita,
    props: true
  },

  // Rutas adicionales del paciente
  {
    path: '/paciente/especialidades',
    name: 'ElegirEspecialidad',
    component: ElegirEspecialidad
  },
  {
    path: '/paciente/citas',
    name: 'VerCitasPaciente',
    component: VerCitasPaciente
  },
  {
    path: '/paciente/ver-citas',
    name: 'VerMisCitas',
    component: VerMisCitas
  },
  {
     path: '/paciente/historial',
     name: 'HistorialMedico',
     component: HistorialMedico
   },
  {
    path: '/paciente/cancelar',
    name: 'CancelarCita',
    component: CancelarCita
  },
  {
    path: '/paciente/reprogramar',
    name: 'ReprogramarCita',
    component: ReprogramarCita
  },

  // Rutas de medicamentos
  {
    path: '/medicamentos/registrar',
    name: 'RegistrarMedicamento',
    component: RegistrarMedicamento
  },
  {
    path: '/medicamentos',
    name: 'ListarMedicamentos',
    component: ListarMedicamentos
  },

  // Rutas administrativas
  {
    path: '/admin',
    name: 'Administrativo',
    component: Administrativo
  },
  {
    path: '/inventario',
    name: 'UsuarioInventario',
    component: UsuarioInventario
  },

  // Rutas de recetas
  {
    path: '/medico/recetas/nueva',
    name: 'RegistrarReceta',
    component: RegistrarReceta
  },
  {
    path: '/inventario/recetas',
    name: 'VerRecetas',
    component: VerRecetas
  },

  // Rutas de stock
  {
    path: '/inventario/stock',
    name: 'VerStock',
    component: VerStock
  },
  {
    path: '/inventario/stock/actualizar',
    name: 'RegistrarStock',
    component: RegistrarStock
  },

  // Rutas del médico
  {
    path: '/medico/citas',
    name: 'VerCitasMedico',
    component: () => import('../views/VerCitasMedico.vue')
  },
  {
    path: '/medico/historial',
    name: 'RegistrarHistorialMedico',
    component: () => import('../views/RegistrarHistorialMedico.vue')
  },
  {
    path: '/medico/finalizar',
    name: 'FinalizarCita',
    component: () => import('../views/FinalizarCita.vue')
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router