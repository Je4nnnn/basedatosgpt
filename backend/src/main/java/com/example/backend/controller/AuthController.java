// backend/src/main/java/com/example/backend/controller/AuthController.java
package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.model.Usuario;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Autentica un usuario y devuelve su ID, email y rol.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuarioOptional =
                authService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getPassword());

        Map<String, String> respuesta = new HashMap<>();

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            respuesta.put("mensaje", "Login exitoso");
            // Añadimos id y email al mapa de respuesta:
            respuesta.put("id", usuario.getId().toString());
            respuesta.put("email", usuario.getEmail());
            respuesta.put("rol", usuario.getRol());
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "Email o contraseña incorrectos");
            return ResponseEntity.status(401).body(respuesta);
        }
    }
}