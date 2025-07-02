<template>
  <div class="paciente-agendar-cita-container">
    <ElegirEspecialidad
      v-if="currentStep === 'select-specialty'"
      @specialty-selected="handleSpecialtySelected"
    />

    <MedicosEspecialidad
      v-if="currentStep === 'select-doctor'"
      :especialidad="selectedEspecialidadCode"
      @volver="handleBackToSpecialties"
      @seleccionarMedico="handleDoctorSelected"
    />

    <VerDisponibilidad
      v-if="currentStep === 'view-availability'"
      :id="selectedMedicoId"
      :nombre="selectedMedicoNombre"
      :especialidad="selectedEspecialidadCode"
      @volver="handleBackToDoctors"
      @agendar="handleScheduleAppointment"
    />

    <AgendarCita
      v-if="currentStep === 'confirm-appointment'"
      :medicoId="selectedMedicoId"
      :nombreMedico="selectedMedicoNombre"
      :especialidad="selectedEspecialidadCode"
      :fechaHoraInicio="selectedFechaHoraInicio"
      @volver="handleBackToAvailability"
      @cita-agendada="handleAppointmentConfirmed"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

import ElegirEspecialidad from './ElegirEspecialidad.vue'
import MedicosEspecialidad from './MedicosEspecialidad.vue'
import VerDisponibilidad from './VerDisponibilidad.vue'
import AgendarCita from './AgendarCita.vue'

const router = useRouter()

const currentStep = ref('select-specialty') // Initial step

// State for selected items
const selectedEspecialidadCode = ref('')
const selectedMedicoId = ref(null)
const selectedMedicoNombre = ref('')
const selectedFechaHoraInicio = ref('')

// Handlers for each step
const handleSpecialtySelected = (especialidad) => {
  selectedEspecialidadCode.value = especialidad.codigo
  currentStep.value = 'select-doctor'
}

const handleBackToSpecialties = () => {
  selectedEspecialidadCode.value = ''
  currentStep.value = 'select-specialty'
}

const handleDoctorSelected = (medico) => {
  selectedMedicoId.value = medico.id
  selectedMedicoNombre.value = medico.nombre
  selectedEspecialidadCode.value = medico.especialidad // Ensure especialidad is passed for availability view
  currentStep.value = 'view-availability'
}

const handleBackToDoctors = () => {
  selectedMedicoId.value = null
  selectedMedicoNombre.value = ''
  currentStep.value = 'select-doctor'
}

const handleScheduleAppointment = (disp) => {
  selectedFechaHoraInicio.value = disp.fechaHoraInicio
  currentStep.value = 'confirm-appointment'
}

const handleBackToAvailability = () => {
  selectedFechaHoraInicio.value = ''
  currentStep.value = 'view-availability'
}

const handleAppointmentConfirmed = () => {
  // After appointment is confirmed, redirect to the home page or a "view appointments" page
  alert('Cita agendada exitosamente y confirmada!')
  router.push('/paciente/ver-citas') // Redirect to a page where they can see their appointments
  // Or if you want to go to a generic home page: router.push('/')
}
</script>

<style scoped>
.paciente-agendar-cita-container {
  padding: 1rem;
}
</style>