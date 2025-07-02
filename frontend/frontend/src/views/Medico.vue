<template>
  <div class="medico-container">
    <h1>Bienvenido, Médico</h1>
    <p>Aquí podrás gestionar tu agenda:</p>
    <ul class="menu-list">
      <li><button @click="goTo('VerCitasMedico')">Ver mis citas</button></li>



    </ul>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
// Asumimos que tras el login guardas: localStorage.setItem('usuarioEmail', email)
const email = localStorage.getItem('usuarioEmail') || ''

/**
 * Navega a la ruta indicada, pasando el email como query param.
 * Las rutas deben estar definidas en src/router/index.js con estos names.
 */
function goTo(name) {
  router.push({
    name,
    query: { email }
  })
}
</script>

<style scoped>
.medico-container {
  max-width: 600px;
  margin: 2rem auto;
  text-align: center;
}
.menu-list {
  list-style: none;
  padding: 0;
  margin-top: 1.5rem;
}
.menu-list li {
  margin: 0.75rem 0;
}
button {
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border: none;
  border-radius: 4px;
  background-color: #0275d8;
  color: white;
  cursor: pointer;
}
button:hover {
  background-color: #025aa5;
}
</style>
