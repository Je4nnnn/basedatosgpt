package com.example.backend.controller;

import com.example.backend.dto.RecetaMedicaDTO;
import com.example.backend.model.RecetaMedica;
import com.example.backend.model.Usuario;
import com.example.backend.service.AuthService;
import com.example.backend.service.RecetaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaMedicaController {

    @Autowired private RecetaMedicaService recetaMedicaService;
    @Autowired private AuthService authService;

    /** 1) MÉDICO crea receta **/
    @PostMapping
    public ResponseEntity<?> crearReceta(
            @RequestParam String email,
            @RequestBody RecetaMedicaDTO dto
    ) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"MEDICO".equals(u.get().getRol())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Solo MÉDICOS pueden registrar recetas.");
        }
        RecetaMedica r = recetaMedicaService.registrarReceta(dto, u.get().getId());
        return ResponseEntity.ok(r);
    }

    /** 2) MÉDICO o INVENTARIO listan recetas **/
    @GetMapping
    public ResponseEntity<?> listarRecetas(@RequestParam String email) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }
        String rol = u.get().getRol();
        List<RecetaMedica> recetas;
        if ("MEDICO".equals(rol)) {
            recetas = recetaMedicaService.listarRecetasPorMedico(u.get().getId());
        } else if ("INVENTARIO".equals(rol)) {
            recetas = recetaMedicaService.listarRecetasPorInventario();
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Solo MÉDICOS o INVENTARIO pueden ver recetas.");
        }
        return ResponseEntity.ok(recetas);
    }

    /** 3) INVENTARIO confirma entrega y elimina receta **/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReceta(
            @RequestParam String email,
            @PathVariable Long id
    ) {
        Optional<Usuario> u = authService.buscarUsuarioPorEmail(email);
        if (u.isEmpty() || !"INVENTARIO".equals(u.get().getRol())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Solo INVENTARIO puede confirmar entrega de recetas.");
        }
        boolean ok = recetaMedicaService.eliminarRecetaPorId(id);
        if (ok) {
            return ResponseEntity.ok("Receta entregada y eliminada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada.");
        }
    }
}
