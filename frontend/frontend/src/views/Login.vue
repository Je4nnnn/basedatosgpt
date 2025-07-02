<!-- frontend/src/views/Login.vue -->
<template>
  <div class="login-container">
    <h1>Iniciar Sesión</h1>
    <form @submit.prevent="login">
      <div class="field">
        <label for="email">Email:</label>
        <input id="email" v-model="email" type="email" required />
      </div>
      <div class="field">
        <label for="password">Contraseña:</label>
        <input id="password" v-model="password" type="password" required />
      </div>
      <button type="submit">Ingresar</button>
    </form>
    <p class="register-link">
      ¿No tienes cuenta?
      <router-link :to="{ name: 'RegistrarUsuario' }">
        Regístrate aquí
      </router-link>
    </p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const router = useRouter()

const login = async () => {
  try {
    // 1) Hacer POST al endpoint de autenticación
    const resp = await axios.post('http://localhost:8081/api/auth/login', {
      email: email.value,
      password: password.value
    })
    const user = resp.data

    // 2) Guardar datos en localStorage
    localStorage.setItem('usuarioEmail', user.email)
    localStorage.setItem('usuarioRol', user.rol)
    localStorage.setItem('usuarioId', user.id)

    // 3) Redirigir según rol
    switch (user.rol) {
      case 'PACIENTE':
        router.push({ name: 'Paciente' })
        break
      case 'MEDICO':
        router.push({ name: 'Medico' })
        break
      case 'ADMINISTRATIVO':
        router.push({ name: 'Administrativo' })
        break
      case 'INVENTARIO':
        router.push({ name: 'UsuarioInventario' })
        break
      default:
        router.push({ name: 'Login' })
    }
  } catch (e) {
    alert('Error al iniciar sesión: ' + (e.response?.data || e.message))
  }
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 2rem auto;
  padding: 2rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  background: #fafafa;
}
.field {
  margin-bottom: 1rem;
}
label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 0.5rem;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}
button:hover {
  background-color: #0056b3;
}
.register-link {
  text-align: center;
  margin-top: 1rem;
}
.register-link a {
  color: #007bff;
  text-decoration: none;
  font-weight: bold;
}
.register-link a:hover {
  text-decoration: underline;
}
</style>
