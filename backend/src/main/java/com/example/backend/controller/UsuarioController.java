// backend/src/main/java/com/example/backend/controller/UsuarioController.java
package com.example.backend.controller;

import com.example.backend.model.Usuario;
import com.example.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints CRUD para Usuario.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /** GET  /api/usuarios → lista todos los usuarios */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> todos = usuarioService.listarUsuarios();
        return ResponseEntity.ok(todos);
    }

    /** GET  /api/usuarios/{id} → busca usuario por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUno(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** POST /api/usuarios → crea un nuevo usuario */
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }
}

