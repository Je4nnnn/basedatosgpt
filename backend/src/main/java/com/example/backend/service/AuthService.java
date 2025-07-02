package com.example.backend.service;

import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para autenticar usuario por email y password
    public Optional<Usuario> autenticarUsuario(String email, String password) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();
    }

    // Método para buscar usuario solo por email (sin password)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    // Método para listar todos los usuarios (opcional si quieres agregar funciones administrativas después)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}


