<template>
  <div>
    <h1>Medicamentos</h1>
    <ul>
      <li v-for="m in medicamentos" :key="m.id">
        {{ m.nombre }} — {{ m.descripcion }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const medicamentos = ref([])

onMounted(async () => {
  const resp = await axios.get('http://localhost:8081/api/medicamentos')
  medicamentos.value = resp.data
})
</script>
