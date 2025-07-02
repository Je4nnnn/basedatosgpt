<!-- frontend/src/views/Administrativo.vue -->
<template>
  <div class="administrativo-container">
    <h1>Panel Administrativo</h1>
    <nav>
      <ul>
        <li>
          <router-link :to="{ name: 'RegistrarMedicamento' }">
            Registrar Medicamento
          </router-link>
        </li>
        <li>
          <router-link :to="{ name: 'ListarMedicamentos' }">
            Ver Medicamentos
          </router-link>
        </li>
        <!-- aquí podrías añadir más enlaces a otras funciones administrativas -->
      </ul>
    </nav>
  </div>
</template>

<script setup>
// No necesitas lógica adicional aquí todavía
</script>

<style scoped>
.administrativo-container {
  max-width: 600px;
  margin: 2rem auto;
}
nav ul {
  list-style: none;
  padding: 0;
}
nav li {
  margin-bottom: 0.5rem;
}
</style>
