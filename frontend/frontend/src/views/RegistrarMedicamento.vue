<template>
  <div class="registrar-medicamento">
    <h1>Registrar Medicamento</h1>
    <form @submit.prevent="registrar">
      <div class="field">
        <label for="nombre">Nombre:</label>
        <input id="nombre" v-model="nombre" type="text" required />
      </div>
      <div class="field">
        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" v-model="descripcion"></textarea>
      </div>
      <div class="field">
        <label for="cantidad">Cantidad disponible:</label>
        <input
          id="cantidad"
          v-model.number="cantidad"
          type="number"
          min="1"
          required
        />
      </div>
      <button type="submit">Guardar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const nombre      = ref('')
const descripcion = ref('')
const cantidad    = ref(1)
const router      = useRouter()

const registrar = async () => {
  try {
    // 1) Registro el medicamento (solo nombre/descr)
    const respMed = await axios.post(
      'http://localhost:8081/api/medicamentos',
      {
        nombre:      nombre.value,
        descripcion: descripcion.value
      }
    )
    const med = respMed.data

    // 2) Con el ID del med creé el stock
    await axios.post(
      'http://localhost:8081/api/stock',
      {
        cantidadDisponible: cantidad.value,
        medicamento: { id: med.id }
      }
    )

    alert('Medicamento y stock registrados correctamente')
    router.push({ name: 'ListarMedicamentos' })
  } catch (e) {
    alert('Error al registrar medicamento: ' + (e.response?.data || e.message))
  }
}
</script>

<style scoped>
.registrar-medicamento { max-width: 400px; margin: 2rem auto; }
.field { margin-bottom: 1rem; }
label { display: block; margin-bottom: .5rem; }
input, textarea { width: 100%; padding: .5rem; box-sizing: border-box; }
button { padding: .75rem 1.5rem; background: #007b00; color: #fff; border: none; cursor: pointer; }
button:hover { background: #005900; }
</style>
