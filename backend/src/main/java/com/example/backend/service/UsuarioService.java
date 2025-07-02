// backend/src/main/java/com/example/backend/service/UsuarioService.java
package com.example.backend.service;

import com.example.backend.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Operaciones de negocio sobre Usuarios.
 */
public interface UsuarioService {

    /**
     * Busca un usuario por su ID.
     */
    Optional<Usuario> buscarUsuarioPorId(Long id);

    /**
     * Devuelve todos los usuarios.
     */
    List<Usuario> listarUsuarios();

    /**
     * Crea un nuevo usuario (o actualiza si ya existe ID).
     */
    Usuario crearUsuario(Usuario usuario);
}
