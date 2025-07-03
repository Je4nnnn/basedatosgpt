<template>
  <header class="p-4 bg-gray-100 flex space-x-4">
    <router-link to="/"       class="hover:underline">Inicio</router-link>
    <router-link to="/paciente" class="hover:underline">Paciente</router-link>
    <router-link to="/medico"   class="hover:underline">Médico</router-link>
    <!-- Aquí el acceso a Admin -->
    <router-link to="/admin"    class="hover:underline">Administrador</router-link>
  </header>

  <main class="p-4">
    <router-view />
  </main>
</template>

<script setup>
// no hace falta importar <router-link> ni <router-view>
</script>

<style scoped>
/* tus estilos… */
</style>
