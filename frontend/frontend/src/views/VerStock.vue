<template>
  <div class="ver-stock">
    <h1>Stock de Medicamentos</h1>

    <ul v-if="stock.length">
      <li v-for="item in stock" :key="item.id">
        <strong>{{ item.medicamento.nombre }}</strong>:
        {{ item.cantidadDisponible }} unidades
      </li>
    </ul>
    <p v-else>No hay stock disponible.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const stock = ref([])

onMounted(async () => {
  try {
    const res = await axios.get('http://localhost:8081/api/stock')
    stock.value = res.data
  } catch (e) {
    alert('Error al cargar el stock: ' + (e.response?.data?.error || e.message))
  }
})
</script>

<style scoped>
.ver-stock {
  max-width: 600px;
  margin: 2rem auto;
  font-family: sans-serif;
}
</style>
