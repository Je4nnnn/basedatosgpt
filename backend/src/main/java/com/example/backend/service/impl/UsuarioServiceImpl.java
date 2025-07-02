// backend/src/main/java/com/example/backend/service/impl/UsuarioServiceImpl.java
package com.example.backend.service.impl;

import com.example.backend.model.Usuario;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación JPA de UsuarioService.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    // 🔥 NUEVOS MÉTODOS PARA ESPECIALIDAD - AGREGAR ESTOS
    public List<Usuario> buscarMedicosPorEspecialidad(String especialidad) {
        return usuarioRepository.findByRolAndEspecialidad("MEDICO", especialidad);
    }

    public List<Usuario> listarMedicos() {
        return usuarioRepository.findByRol("MEDICO");
    }

    public Usuario actualizarEspecialidad(Long usuarioId, String especialidad) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setEspecialidad(especialidad);
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuario no encontrado");
    }
}
