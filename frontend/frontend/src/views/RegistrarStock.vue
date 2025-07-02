<template>
  <div>
    <h1>Actualizar Stock</h1>
    <form @submit.prevent="guardar">
      <label>ID Medicamento:
        <input v-model.number="medicamentoId" type="number" required/>
      </label>
      <label>Cantidad:
        <input v-model.number="cantidad" type="number" required/>
      </label>
      <button type="submit">Guardar</button>
    </form>
  </div>
</template>
<script setup>
import { ref } from 'vue'
import axios from 'axios'
const medicamentoId = ref(null)
const cantidad      = ref(0)
async function guardar() {
  await axios.post('http://localhost:8081/api/stock', {
    medicamento: { id: medicamentoId.value },
    cantidadDisponible: cantidad.value
  })
  alert('Stock actualizado')
}
</script>
