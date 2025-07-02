<template>
  <div class="registrar-container">
    <h1>Registrar Usuario</h1>
    <form @submit.prevent="registrar">
      <div class="field">
        <label for="nombre">Nombre:</label>
        <input id="nombre" v-model="nombre" type="text" required />
      </div>
      <div class="field">
        <label for="email">Email:</label>
        <input id="email" v-model="email" type="email" required />
      </div>
      <div class="field">
        <label for="password">Contraseña:</label>
        <input id="password" v-model="password" type="password" required />
      </div>
      <div class="field">
        <label for="rol">Rol:</label>
        <select id="rol" v-model="rol">
          <option value="PACIENTE">Paciente</option>
          <option value="MEDICO">Médico</option>
          <option value="ADMINISTRATIVO">Administrativo</option>
          <option value="INVENTARIO">Inventario</option>
        </select>
      </div>
      <button type="submit">Registrar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const nombre   = ref('')
const email    = ref('')
const password = ref('')
const rol      = ref('PACIENTE')
const router   = useRouter()

const registrar = async () => {
  try {
    await axios.post('http://localhost:8081/api/usuarios', {
      nombre:   nombre.value,
      email:    email.value,
      password: password.value,
      rol:      rol.value
    })
    alert('Usuario registrado correctamente')
    router.push('/')
  } catch (e) {
    alert('Error al registrar usuario: ' + (e.response?.data?.error || e.message))
  }
}
</script>

<style scoped>
.registrar-container {
  max-width: 400px;
  margin: 2rem auto;
}
.field {
  margin-bottom: 1rem;
}
label {
  display: block;
  margin-bottom: .5rem;
}
input, select {
  width: 100%;
  padding: .5rem;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: .75rem;
  background-color: #007b00;
  color: white;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #005900;
}
</style>
