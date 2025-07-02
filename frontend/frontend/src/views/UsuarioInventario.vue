<!-- frontend/src/views/UsuarioInventario.vue -->
<template>
  <div class="inventario-container">
    <h1>Panel de Inventario</h1>
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
        <<router-link to="/inventario/recetas">Ver Recetas</router-link>>

        <<router-link to="/inventario/stock">Ver Stock</router-link>>
        <<router-link to="/inventario/stock/actualizar">Actualizar Stock</router-link>>
      </ul>
    </nav>
  </div>
</template>

<script setup>
// Lógica vacía por ahora
</script>

<style scoped>
.inventario-container {
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
